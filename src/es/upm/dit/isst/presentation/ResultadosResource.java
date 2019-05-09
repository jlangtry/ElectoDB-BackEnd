package es.upm.dit.isst.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import es.upm.dit.isst.logic.CalculaMetricasImplementation;

import es.upm.dit.isst.persistence.dao.ProvinciaDAO;
import es.upm.dit.isst.persistence.dao.ProvinciaDAOImplementation;
import es.upm.dit.isst.persistence.dao.VotosDAO;
import es.upm.dit.isst.persistence.dao.VotosDAOImplementation;
import es.upm.dit.isst.persistence.model.Provincia;
import es.upm.dit.isst.persistence.model.Votos;
import es.upm.dit.isst.persistence.model.Partido;

@Path("resultados")
public class ResultadosResource {
	
	private static Resultados resultado;
	
	public static Provincia prov;
	
	public ResultadosResource(){
		
	}
	
	static {
		 resultado = new Resultados("PSOE", 4500000);
		 prov = new Provincia("Spain", 50);
	}
	
	//public String getResultados(@DefaultValue("2016") @QueryParam("anno") String anno ){
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public DatoPrueba getResultados(@DefaultValue("2016") @QueryParam("anno") int anno,
									@DefaultValue("Nacional") @QueryParam("provincia") String provincia,
									@DefaultValue("Dhondt") @QueryParam("leyEscano") String leyEscanos){
		
		//CalculaMetricasImplementation cmi = new CalculaMetricasImplementation();
		//cmi.readPartidos();
		//cmi.readProvincias();
		//cmi.readVotos();
		VotosDAO vdao = VotosDAOImplementation.getInstance();
		
		List<Votos> votosAnnoProv = vdao.filtroPorAnnoYProvincia(anno, provincia);
		Votos[] arrayVotosProv = new Votos[votosAnnoProv.size()];
		arrayVotosProv = votosAnnoProv.toArray(arrayVotosProv);
		votos[] votoss = new votos[arrayVotosProv.length];
		
		for(int i = 0; i < votoss.length; i++) {
			int votos = arrayVotosProv[i].getVotos();
			String nombrePartido = "null";
			Partido partido = arrayVotosProv[i].getPart();
			if(partido != null) nombrePartido = partido.getIdNombre();
			votoss[i] = new votos(nombrePartido, votos, 13);
		}
		
		List<Votos> votosAnno = vdao.filtroPorAnno(anno);
		Votos[] arrayVotos = new Votos[votosAnno.size()];
		arrayVotos = votosAnno.toArray(arrayVotos);
		mapa[] mapas = new mapa[52];
		
		String prov = "null";
		Provincia testNullProv = arrayVotos[0].getProv();
		if (testNullProv != null) prov = testNullProv.getIdNombre();
		String color = "null";
		String partido = "null";
		Partido testNullPartido = arrayVotos[0].getPart();
		if (testNullPartido != null) {
			color = testNullPartido.getColor();
			partido = testNullPartido.getIdNombre();
		}
		int votos = arrayVotos[0].getVotos();
		mapas[0] = new mapa (prov, color, partido, votos);
		int index = 1;
		for (int i = 1; i < 52; i++) {
			if (index == arrayVotos.length) break;
			for(int j = index; j < arrayVotos.length;j++) {
				if (arrayVotos[j].getProv()==null)
					continue;
				else {
					if(arrayVotos[j].getProv().getIdNombre() == mapas[i-1].getProvincia()) continue;
					
					String prov1 = arrayVotos[j].getProv().getIdNombre();
					
					String color1 = "null";
					String partido1 = "null";
					Partido testNullPartido1 = arrayVotos[j].getPart();
					if (testNullPartido1 != null) {
						color = testNullPartido1.getColor();
						partido = testNullPartido1.getIdNombre();
					}
					int votos1 = arrayVotos[j].getVotos();
					mapas[i] = new mapa (prov1, color1, partido1, votos1);
					index = j+1;
					break;
				}
			}
		}
		return new DatoPrueba(mapas, votoss);
	}
}
