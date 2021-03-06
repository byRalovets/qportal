package by.ralovets.qportal.model.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Represents a Java enumerable type that corresponds to a custom enumeration type in PostgreSQL.
 *
 * @author Anton Ralovets
 * @since 1.0
 */
public class PostgreSQLEnumType extends EnumType<FieldType> {

    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        st.setObject(index, value != null ? ((Enum<FieldType>) value).name() : null, Types.OTHER);
    }
}