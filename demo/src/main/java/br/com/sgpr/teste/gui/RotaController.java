package br.com.sgpr.teste.gui;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sgpr.teste.business.Rota;
import br.com.sgpr.teste.business.VisaoRotas;
import br.com.sgpr.teste.business.RotaCidadesService;
import br.com.sgpr.teste.business.RotaService;
import br.com.sgpr.teste.business.VisaoRotaCidade;

@RestController
@RequestMapping(path="rota")
public class RotaController {

    @Autowired
    private RotaService rotaServices;
    @Autowired
    private RotaCidadesService rotaCidadesService;

    @GetMapping()
    public Iterable<VisaoRotas> getRotas(){
        return rotaServices.getRotas();
    }

    @PostMapping()
    public String postRota(@RequestBody Rota rota){
        return rotaServices.saveNewRota(rota);
    }

    @DeleteMapping(path = "/{rotaId}")
    public String deleteRota(@PathVariable String rotaId){
        return rotaServices.deleteRota(rotaId);
    }

    //cidades da rota

    @GetMapping(path="/{rotaId}/cidades")
    public Iterable<VisaoRotaCidade> getCidades(@PathVariable String rotaId){
        return rotaCidadesService.getCidades(rotaId);
    }

    @PostMapping(path="/cidades")
    public String postRotaCidades(@RequestBody ArrayList<VisaoRotaCidade> cidades){
        return rotaCidadesService.saveCidadesDaRota(cidades);
    }

    @DeleteMapping(path = "/{rotaId}/cidades")
    public String deleteCidadesDaRota(@PathVariable String rotaId){
        return rotaCidadesService.deleteCidadesDaRota(rotaId);
    }
}
