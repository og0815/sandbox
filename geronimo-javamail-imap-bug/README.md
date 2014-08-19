Geronimo Javamail Imap Bug
==========================

A Project to show a bug in the javamail implementation of geronimo.

The story so far
----------------
- In the development of the DeutscheWarenwirtschaft, weird classloading problems and errors on mail api usage.
  - The DW is using openejb and apache commons email.

1. Found duplicated implementation.
  - openejb is pulling in org.apache.geronimo.javamail:geronimo-javamail_1.4_mail
  - apache commons email pulling in javamail
2. The geronimo implementation also is not working with imap.
3. Excluded the geronimo implementation and using javax.mail solved the problem till tomee 1.7.0 / openejb 4.7.0
4. In x.7.x the classes org.apache.geronimo.osg.locator.Activator and ProviderLocator were removed from the org.apache.openejb:java-api
   and the geronimo mail package has them as duplicates, providing them now.
5. So if imap is used in openejb either a complexes exclude and reshade is needed or the geronimo implementation must be repaired.

Target
------
Build a simple example to display the errors in the imap implementation. Think about alternatives (POP Auth ex.).
Show the error to the geronimo guys.

State
-----
On running the sandbox.imapbug.Connector with the geronimo dependency and connecting via imap to e.g outlook.com an NPE
is thrown. Changing the dependency to javax.mail and doing the same will result in a successful connection.
Opened Issue https://issues.apache.org/jira/browse/GERONIMO-6526