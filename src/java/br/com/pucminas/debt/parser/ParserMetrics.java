package br.com.pucminas.debt.parser;

import br.com.pucminas.debt.model.Metrica;
import static br.com.pucminas.debt.model.TipoMetrica.getTipoPorDescr;
import br.com.pucminas.debt.model.ValorMetrica;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException; 
import javax.xml.parsers.SAXParser; 
import javax.xml.parsers.SAXParserFactory; 

import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler;

public class ParserMetrics extends DefaultHandler { 
	
    private final List<Metrica> metricas = new ArrayList<>();
    private Metrica metrica = new Metrica();
    private List<ValorMetrica>values = new ArrayList<>();
    private ValorMetrica valor = new ValorMetrica();
	
    public ParserMetrics() { 
        super(); 
    } 
	
    public List<Metrica> fazerParsing(String pathArq) { 
	
        SAXParserFactory factory = SAXParserFactory.newInstance(); 
        SAXParser saxParser; 
        try { 
            saxParser = factory.newSAXParser(); 
            System.out.println(pathArq);
            saxParser.parse(pathArq, this);  
        } catch (ParserConfigurationException | SAXException | IOException e) { 
            StringBuffer msg = new StringBuffer(); 
            msg.append("Erro:\n"); 
            msg.append(e.getMessage() + "\n"); 
            msg.append(e.toString()); 
            System.out.println(msg); 
        } 
        return metricas;
    }  

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) { 
	if (qName.compareTo("Metric") == 0) { 
            metrica.setTipo(getTipoPorDescr(atts.getValue(1)));
	}
		
	if (qName.compareTo("Value") == 0) { 
            if(atts.getValue(1) != null){
                valor.setName(atts.getValue(0));
                if(atts.getValue(3) != null){
                    valor.setSource(atts.getValue(1));
                    String val = atts.getValue(3).replace(",", ".");
                    valor.setValor(Float.parseFloat(val));
                    valor.setPack(atts.getValue(2));
                }
                else{
                    valor.setPack(atts.getValue(1));
                    String val = atts.getValue(2).replace(",", ".");
                    valor.setValor(Float.parseFloat(val));
                }
            }
            else{
                String val = atts.getValue(0).replace(",", ".");
                valor.setValor(Float.parseFloat(val));
            }
        }
                
        values.add(valor);
        valor = new ValorMetrica();
    } 
	
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException { 
        if (qName.compareTo("Metric") == 0) { 
            metrica.setValores(values);
            values = new ArrayList<>();
            metricas.add(metrica);
            metrica = new Metrica();
        }
    } 
}



