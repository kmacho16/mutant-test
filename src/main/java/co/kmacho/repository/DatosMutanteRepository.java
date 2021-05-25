package co.kmacho.repository;

import co.kmacho.config.JdbiContext;
import co.kmacho.model.DatosMutante;
import co.kmacho.services.MutantService;
import org.jdbi.v3.core.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DatosMutanteRepository {
    @Inject
    JdbiContext jdbiContext;
    private static final Logger LOG = LoggerFactory.getLogger(DatosMutanteRepository.class);

    public boolean hashRegistrado(String hash){
        try(Handle handle = jdbiContext.getHandle()){
            DatosMutanteDao datosMutanteDao = handle.attach(DatosMutanteDao.class);
            return datosMutanteDao.cuntByHash(hash)>0;
        }catch (Exception e){
            LOG.error("ERROR {}",e);
        }
        return true;
    }

    public void create(DatosMutante datosMutante){
        try(Handle handle = jdbiContext.getHandle()){
            DatosMutanteDao datosMutanteDao = handle.attach(DatosMutanteDao.class);
            datosMutanteDao.create(datosMutante);
        }catch (Exception e){
            LOG.error("ERROR {}",e);
        }
    }

    public List<DatosMutante> getAll(){
        try(Handle handle = jdbiContext.getHandle()){
            DatosMutanteDao datosMutanteDao = handle.attach(DatosMutanteDao.class);
            return datosMutanteDao.getAll();
        }catch (Exception e){
            LOG.error("ERROR {}",e);
        }
        return new ArrayList<>();
    }
}
