package co.kmacho.repository;

import co.kmacho.model.DatosMutante;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;


public interface DatosMutanteDao {

    @SqlQuery("select count(id) from datos_mutante where cadena_hash = :hash ")
    long cuntByHash(String hash);

    @SqlUpdate("insert into datos_mutante(cadena_hash,cadena_dna,cadena_mutante,is_mutant) values(:cadenaHash, :cadenaDna, " +
            ":cadenaMutante, :is_mutant)")
    void create(@BindBean DatosMutante datosMutante);

    @SqlQuery("select cadena_hash,cadena_dna,cadena_mutante,is_mutant from datos_mutante")
    @RegisterRowMapper(DatosMutanteMapper.class)
    List<DatosMutante> getAll();
}
