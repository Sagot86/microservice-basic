package org.example.basic.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * Hibernate naming strategy.
 *
 * @author Igor_Orlov
 */
public class HibernateNamingStrategy extends SpringPhysicalNamingStrategy {

    private static final String PREFIX = "basic_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return super.toPhysicalTableName(addPrefix(name), jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return super.toPhysicalSequenceName(addPrefix(name), jdbcEnvironment);
    }

    private Identifier addPrefix(Identifier identifier) {
        String name = identifier.getText();
        return name.startsWith(PREFIX) ? identifier : Identifier.toIdentifier(PREFIX + name);
    }
}
