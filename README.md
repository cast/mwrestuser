mwrestuser
==========

REST user resource

Assumptions:

Java EE environment: EE 7, JAX-RS, JPA specification (war implementation: eclipselink) (jboss as arquillian implementation: hibernate)

Simple Design 
REST SERVICE  ->  DAO

CRUD design where update equals PUT

Simple jpql queries for DAO (TODO: criteria/named queries)

Container managed transactions and database, default java ee database for war package, java:jboss/datasources/ExampleDS for JBOSS managed Arquillian profile

Arquillian unit and integration testing

