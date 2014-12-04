package eu.ggnet.saft.core.swing;

import eu.ggnet.saft.core.UiCore;
import eu.ggnet.saft.core.all.OkCancelResult;
import eu.ggnet.saft.core.all.UiUtil;
import eu.ggnet.saft.core.aux.*;
import javafx.embed.swing.SwingNode;
import javafx.scene.*;
import javafx.stage.Modality;
import javax.swing.*;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author oliver.guenther
 */
public class SwingSaft {

    public static <T, R extends JPanel> R construct(Class<R> panelClazz, T parameter) throws Exception {
        return dispatch(() -> {
            R panel = panelClazz.getConstructor().newInstance();
            if (panel instanceof Initialiser) {
                ((Initialiser) panel).initialise();
            }
            if (parameter != null && panel instanceof Consumer) {
                try {
                    ((Consumer<T>) panel).accept(parameter);
                } catch (ClassCastException e) {
                    LoggerFactory.getLogger(SwingSaft.class).warn(panel.getClass() + " implements Consumer, but not of type " + parameter.getClass());
                }
            }
            return panel;
        });
    }

    public static <T, R, P extends JComponent> OkCancelResult<R> wrapInChoiceAndShow(Window parent, P panel, Modality modality, R payload) throws ExecutionException, InterruptedException, InvocationTargetException {
        return dispatch(() -> {
            OkCancelDialog<P> dialog = new OkCancelDialog<>(parent, panel);
            dialog.setTitle(UiUtil.title(panel.getClass()));
            dialog.setModalityType(UiUtil.toSwing(modality).orElse(ModalityType.APPLICATION_MODAL));
            dialog.pack();
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);
            return new OkCancelResult<>(payload, dialog.isOk());
        });
    }

    public static <T> T dispatch(Callable<T> callable) throws ExecutionException, InterruptedException, InvocationTargetException {
        FutureTask<T> task = new FutureTask(callable);
        if (EventQueue.isDispatchThread()) task.run();
        else EventQueue.invokeAndWait(task);
        return task.get();
    }

    public static SwingNode wrap(final JPanel p) throws ExecutionException, InterruptedException, InvocationTargetException {
        return dispatch(() -> {
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(p);
            return swingNode;
        });
    }

    /**
     * Special form of {@link SwingUtilities#getWindowAncestor(java.awt.Component) }, as it also verifies if the
     * supplied parameter is of type Window and if true returns it.
     *
     * @param c the component
     * @return a window.
     */
    public static Optional<Window> windowAncestor(Component c) {
        if (c == null) return Optional.empty();
        if (c instanceof Window) return Optional.of((Window) c);
        return Optional.ofNullable(SwingUtilities.getWindowAncestor(c));
    }

    /**
     * Returns the Swing Window in Swing Mode from a wrapped JavaFx Node.
     *
     * @param p the node
     * @return a window
     */
    public static Optional<Window> windowAncestor(Node p) {
        if (p == null) return Optional.empty();
        return windowAncestor(UiCore.swingParentHelper.get(p.getScene()));
    }

    public static void enableCloser(Window window, Object uiElement) {
        if (uiElement instanceof ClosedListener) {
            window.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosed(WindowEvent e) {
                    ((ClosedListener) uiElement).closed();
                }

            });
        }
    }

    public static java.util.List<Image> loadIcons(Class<?> reference) throws IOException {
        java.util.List<String> files = IOUtils.readLines(reference.getResourceAsStream("."), Charsets.UTF_8);

        String head = UiCore.CLASS_SUFFIXES_FOR_ICONS
                .stream()
                .filter(s -> reference.getSimpleName().endsWith(s))
                .map(s -> reference.getSimpleName().substring(0, reference.getSimpleName().length() - s.length()))
                .findFirst()
                .orElse(reference.getSimpleName());

        String pattern = "^" + head + "Icon.*.(png|gif|jpg)$";
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        return files.stream().filter(t -> Pattern.matches(pattern, t)).map(t -> toolkit.getImage(reference.getResource(t))).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String head = "Base";
        String pattern = "^" + head + "Icon.*.(png|gif|jpg)$";
        System.out.println("Pattern:" + pattern);
        for (String file : Arrays.asList("BaseIcon.png", "Muh", "BaseIcon.jpg", "BaseIcon_1.jpg", "BaseIconDDDD.jpg")) {
            if (Pattern.matches(pattern, file)) System.out.println("Match:" + file);
            else System.out.println("No Match:" + file);
        }

    }

}
