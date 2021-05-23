package co.kmacho.services;

import co.kmacho.model.Dna;
import co.kmacho.model.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class MutantService {

    private static final Logger LOG = LoggerFactory.getLogger(MutantService.class);
    private int count = 0;


    public Boolean isMutant(String[] dna){
        count=0;
        ArrayList<String[]> matriz= new ArrayList<>();
        for (String cadena : dna ){
            matriz.add(cadena.split(""));
        }
        return validateOblique(matriz);
    }

    private Boolean validateOblique(ArrayList<String[]> matriz){
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
                List<String> aaa = filter.stream().map(i->i.getLeter()).collect(Collectors.toList());
                cadenas.addAll(aaa);
            }
            }while (order_pos<4);
            LOG.info("*** {}",cadenas);
            count++;
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
                LOG.info("target  {}",target);
                LOG.info("letter  {}",letter);
                LOG.info("acount  {}",acount);
                return validarteCoord(matriz,target,nextRow,nextCol,acount+1,opt);
            }
        }
        return new Dna(target,acount);
    }
}
