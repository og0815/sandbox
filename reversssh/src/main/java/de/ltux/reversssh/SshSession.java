/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.reversssh;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Ssh Session Engine. Allows creation of selective Ssh Sessions in ControlMode Master. It can also detect all created and active session before. So even if the
 * java process ends and gets startet again it keeps track of the manages ssh session.
 * <b>Needs: ssh and sshpass on the path.</b>
 *
 * @author oliver.guenther
 */
public class SshSession {

    public static class Status { // Record Candiate.

        /**
         * Exitcode of the status call.
         */
        private final int exitCode;
        /**
         * Process id of the master process.
         */
        private final int pid;
        /**
         * Alive status of the master process.
         */
        private final boolean alive;
        /**
         * STDOut message of the status call.
         */
        private final String message;

        public Status(int exitCode, int pid, boolean alive, String message) {
            this.exitCode = exitCode;
            this.pid = pid;
            this.alive = alive;
            this.message = message;
        }

        public int exitCode() {
            return exitCode;
        }

        public int pid() {
            return pid;
        }

        public boolean isAlive() {
            return alive;
        }

        public String message() {
            return message;
        }

        @Override
        public String toString() {
            return "Status{" + "exitCode=" + exitCode + ", pid=" + pid + ", alive=" + alive + ", message=" + message + '}';
        }

    }

    public final static String CONTROL_FILE_START = "reverse-ssh-ctl-";
    public final static String CONTROL_DIRECOTRY = "/tmp";
    public final static String LOG_FILE_START = "reverse-ssh-log-";
    public final static String LOG_DIRECTORY = "/tmp";
    private final static String RUNNING_PATTERN = "Master running \\(pid=([0-9]+)\\)";

    /**
     * Host of this session.
     */
    private final String host;

    /**
     * Contains startup errors or empty if none.
     */
    private final String startupError;

    SshSession(String host, String startupError) {
        this.host = host;
        this.startupError = startupError;
    }

    /**
     * Returns a list of SshSessions with a ControllMaster based on the existing contoll file handles.
     *
     * @return a list of SshSesssions
     */
    public static List<SshSession> listMaster() {
        List<File> files = Arrays.asList(new File(CONTROL_DIRECOTRY).listFiles((File dir, String name) -> name.startsWith(CONTROL_FILE_START)));
        if (files.isEmpty()) {
            return Collections.emptyList();
        }
        return files.stream().map(f -> new SshSession(f.getName().substring(CONTROL_FILE_START.length()), "")).collect(Collectors.toList());
    }

    public static SshSession create(RequestReverseSsh request) {
        // Todo: Optimize at least with environment pass.
        List<String> command = Arrays.asList(
                "/usr/bin/sshpass", "-e",
                "ssh",
                "-f", // Background
                "-N", "-T", // No Shell, No Pty
                "-M", "-S", CONTROL_DIRECOTRY + "/" + CONTROL_FILE_START + request.getHost(), // Use a control File.
                "-R", "" + request.getPort() + ":localhost:22", // Reverse Tunnel
                "-o", "StrictHostKeyChecking=no",
                "-o", "VerifyHostKeyDNS=no",
                "-o", "CheckHostIP=no",
                "-v", // Verbose
                "-E", LOG_DIRECTORY + "/" + LOG_FILE_START + request.getHost(), // Store the output
                request.getUsername() + "@" + request.getHost()
        );

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().put("SSHPASS", request.getPassword());
        try {
            var process = pb.start();
            var result = process.waitFor();

            return new SshSession(request.getHost(), result == 0 ? "" : "Create exited with " + result);
        } catch (IOException | InterruptedException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            return new SshSession(request.getHost(), errors.toString() + "\n");
        }
    }

    /**
     * Returns a multiline log of everything. Contations startup errors and the output log of the session, if any.
     *
     * @return a multiline log of everything.
     */
    public String getLog() {
        File logFile = new File(LOG_DIRECTORY, LOG_FILE_START + host);
        if (logFile.exists() && logFile.canRead()) {
            try {
                return startupError + Files.readString(logFile.toPath());
            } catch (IOException ex) {
                StringWriter errors = new StringWriter();
                ex.printStackTrace(new PrintWriter(errors));
                return startupError + errors.toString();
            }
        } else {
            return startupError + "Logfile: " + logFile + " not found";
        }
    }

    /**
     * Returns a Status object for this connection.
     *
     * @return a Status object for this connection.
     */
    public Status getStatus() {
        List<String> command = Arrays.asList(
                "ssh",
                "-S", CONTROL_DIRECOTRY + "/" + CONTROL_FILE_START + host, // Use a control File.
                "-O", "check", // Check Command
                host);
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            var process = pb.start();

            var exitCode = process.waitFor();
            var is = process.getInputStream();
            String message = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();
            var runningPattern = Pattern.compile(RUNNING_PATTERN);
            var m = runningPattern.matcher(message);
            int pid = -1;
            if (m.find()) {
                pid = Integer.parseInt(m.group(1));
            }
            return new Status(exitCode, pid, pid > 0, message);
        } catch (IOException | InterruptedException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            return new Status(-1, -1, false, errors.toString());
        }
    }

    /**
     * Tries to end the master process.
     *
     * @return An empty string, if call was succesfull. Otherwise the exit code, console output or exception printout.
     */
    public Optional<String> end() {
        List<String> command = Arrays.asList(
                "ssh",
                "-S", CONTROL_DIRECOTRY + "/" + CONTROL_FILE_START + host, // Use a control File.
                "-O", "exit", // End Command
                host);
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            var process = pb.start();
            var exitCode = process.waitFor();
            if (exitCode == 0) {
                new File(LOG_DIRECTORY, LOG_FILE_START + host).delete(); // Delete Logfile.
                return Optional.empty();
            }
            var is = process.getInputStream();
            String message = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();
            return Optional.of("Exit Code:" + exitCode + "\nConsole Out:\n" + message);
        } catch (IOException | InterruptedException ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            return Optional.of(errors.toString());
        }
    }
}
