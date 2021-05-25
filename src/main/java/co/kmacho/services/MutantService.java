package co.kmacho.services;

import co.kmacho.config.JdbiContext;
import co.kmacho.model.DatosMutante;
import co.kmacho.model.Dna;
import co.kmacho.model.Orders;
import co.kmacho.repository.DatosMutanteRepository;
import org.jdbi.v3.core.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class MutantService {

    private static final Logger LOG = LoggerFactory.getLogger(MutantService.class);
    private int count = 0;

    @Inject
    JdbiContext jdbiContext;

    @Inject
    DatosMutanteRepository datosMutanteRepository;


    public Boolean isMutant(String[] dna){
        ArrayList<String[]> matriz= new ArrayList<>();
        count=0;
        for (String cadena : dna ){
            matriz.add(cadena.split(""));
        }

        return validateOblique(matriz,Arrays.toString(dna));
    }

    public Map<String, String> stats(){
        List<DatosMutante> all =  datosMutanteRepository.getAll();
        Map<String, String> map = new HashMap<>();
        long mutants = all.stream().filter(i->i.isIs_mutant()).collect(Collectors.toList()).size();
        long humans = all.stream().filter(i->!i.isIs_mutant()).collect(Collectors.toList()).size();
        double ratio = (double) mutants / (double) (mutants+humans);
        map.put("count_mutant_dna",String.valueOf( mutants));
        map.put("count_human_dna",String.valueOf( humans));
        map.put("ratio",String.valueOf(ratio));


        return map;
    }

    private Boolean validateOblique(ArrayList<String[]> matriz, String rawDna){
        String[] order = {Orders.DIAGONAL_DERECHA, Orders.DIAGONAL_IZQUIERDA, Orders.HORIZONTAL, Orders.VERTICAL};

        Set<String> cadenas = new HashSet<>();
        for (String[] row : matriz){
            LOG.info("**************** {}",count);
            List<Dna> filter;
            int order_pos = 0;
            do{
                String aux = order[order_pos];
                filter = IntStream.range(0,row.length)
                        .mapToObj(i->validarteCoord(matriz,row[i],count,i,1, aux ))
                        .filter(j->j.getSize()>=4).collect(Collectors.toList());
                order_pos++;
            if (!filter.isEmpty()){
                List<String> aaa = filter.stream().map(i->i.getLeter().repeat(i.getSize())).collect(Collectors.toList());
                cadenas.addAll(aaa);
            }
            }while (order_pos<4);
            LOG.info("*** {}",cadenas);
            count++;
        }
        String hash = generarMd5(rawDna);
        LOG.info("www  {}",rawDna);

        if(!datosMutanteRepository.hashRegistrado(hash)){
            LOG.info("HASH NO EXISTE");
            DatosMutante datosMutante = new DatosMutante();
            datosMutante.setIs_mutant(cadenas.size()>1);
            datosMutante.setCadenaMutante(cadenas.toString());
            datosMutante.setCadenaDna(rawDna);
            datosMutante.setCadenaHash(hash);
            datosMutanteRepository.create(datosMutante);
        }
        return cadenas.size()>1;
    }

    private Dna validarteCoord(ArrayList<String[]> matriz, String target, int targetRow, int targetCol, int acount,String opt){
        int nextRow = 0;
        int nextCol = 0;
        switch (opt){
            case Orders.DIAGONAL_DERECHA:
                nextRow = targetRow+1;
                nextCol = targetCol+1;
                break;
            case Orders.DIAGONAL_IZQUIERDA:
                nextRow = targetRow+1;
                nextCol = targetCol-1;
                break;
            case Orders.HORIZONTAL:
                nextRow = targetRow+1;
                nextCol = targetCol;
                break;
            case Orders.VERTICAL:
                nextRow = targetRow;
                nextCol = targetCol+1;
                break;
        }
        if (nextCol>=0 && nextRow>=0 && nextRow < matriz.size() && nextCol<matriz.get(nextRow).length){
            String letter = matriz.get(nextRow)[nextCol];
            if(letter.equals(target)){
                return validarteCoord(matriz,target,nextRow,nextCol,acount+1,opt);
            }
        }
        return new Dna(target,acount);
    }

    public String generarMd5(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        md5.reset();
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        md5.update(bytes);
        byte[] array = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
}
