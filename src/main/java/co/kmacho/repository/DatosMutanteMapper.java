package co.kmacho.repository;

import co.kmacho.model.DatosMutante;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatosMutanteMapper implements RowMapper<DatosMutante> {

    @Override
    public DatosMutante map(ResultSet rs, StatementContext ctx) throws SQLException {
        DatosMutante datosMutante = new DatosMutante();
        datosMutante.setIs_mutant(rs.getBoolean("is_mutant"));
        datosMutante.setCadenaMutante(rs.getString("cadena_mutante"));
        datosMutante.setCadenaHash(rs.getString("cadena_hash"));
        datosMutante.setCadenaDna(rs.getString("cadena_dna"));
        return datosMutante;
    }
}
