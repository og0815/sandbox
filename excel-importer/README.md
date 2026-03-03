# Excel Importer (Jakarta EE, PrimeFaces, JPA)

This project is a scaffold for importing Excel data into a MariaDB database with update-only comparison.

Quick start (WildFly):

1. Configure a datasource in WildFly with JNDI name `java:/MariaDS` pointing to your MariaDB instance.
2. Build WAR:

```bash
mvn clean package
```

3. Deploy the generated `target/excel-importer.war` to WildFly.

Usage:
- Open the application root page, upload an `.xlsx` file. The app will compare rows (by `PK_IDObject`) with the database and display changed fields and missing references. The current implementation only shows differences and missing references; it does not perform updates automatically.

Notes:
- Adjust `persistence.xml` and datasource JNDI according to your environment.
- The `Property` entity maps the fields described in the specification; adjust types if needed.
- The Excel header must use the exact column names specified in the requirements to map correctly.
