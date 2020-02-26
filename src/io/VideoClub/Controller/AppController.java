/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Controller;

import io.VideoClub.Model.Client;
import io.VideoClub.Model.Enums.GameCategory;
import io.VideoClub.Model.Enums.MovieCategory;
import io.VideoClub.Model.Enums.ProductsTypes;
import io.VideoClub.Model.IClient;
import io.VideoClub.Model.Item;
import io.VideoClub.Model.Juego;
import io.VideoClub.Model.Pelicula;
import io.VideoClub.Model.Product;
import io.VideoClub.Model.Reservation;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Manueh
 */
public class AppController implements IAppController {

    private static AppController instancia = null;

    private Set<Product> productos;
    public Set<IClient> clientes;
    private Set<Reservation> reservas;

    private AppController() {
        productos = new HashSet<>();
        clientes = new HashSet<>();
        reservas = new HashSet<>();

    }

    public static AppController getInstance() {
        instancia = new AppController();
        return instancia;
    }

    @Override
    public Set<Product> listAllProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Product> listAllProducts(Comparator c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Product> listAllByType(ProductsTypes type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Product> listAllByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Product> listAllByName(String name, ProductsTypes type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Product> listAllByStatus(Product.Status status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> listAllDifferentProducts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> listAllDifferentMovies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> listAllDifferentGames() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Product, Integer> listAllAmountOfProducts(String name) {
        Map<Product,Integer> listado=new HashMap<>();
       
        
        return listado;
       
    }

    @Override
    public Map<Product, Integer> listAllAmountOfProducts(ProductsTypes type, String name) {
        Map<Product,Integer> listado=new HashMap<>();
       
        
        return listado;
    }

    @Override
    public Set<IClient> listAllClients() {
        return clientes;
    }

    @Override
    public Set<IClient> listAllClients(Comparator c) {
       Set<IClient> ordenado=new TreeSet<>(c);
       ordenado.addAll(clientes);
       return ordenado;
    }

    @Override
    public Set<IClient> listAllClientsWithReservationsNotFinished() {
        Set<IClient> clientesconreservas=new HashSet<>();
        for(IClient c: clientes){
            for(Reservation re: reservas){
                if(re.cli.equals(re)&&re.status!=Reservation.StatusReserve.FINISHED){
                    clientesconreservas.add(c);
                }
            }
        }
        return clientesconreservas;
    }

    @Override
    public Set<Reservation> listAllReservations() {
        return reservas;
    }

    @Override
    public Set<Reservation> listAllReservations(Comparator c) {
        Set<Reservation> ordenado=new TreeSet<>(c);
        ordenado.addAll(reservas);
        return ordenado;
    }

    @Override
    public Set<Reservation> listAllReservations(Reservation.StatusReserve status) {
        Set<Reservation> ordenado=new HashSet<>();
        for(Reservation re: reservas){
            if(re.status==status){
                ordenado.add(re);
            }
        }
        return ordenado;
    }

    @Override
    public double getIncommings() {
        double resultado=0;
        for(Reservation re:reservas){
            resultado+=re.pro.getPrize();
        }
        return resultado;
    }

    @Override
    public double getIncommings(LocalDate from) {
        double resultado=0;
        for(Reservation reser: reservas){
            if(reser.ini.compareTo(from)>=0){
                resultado+=reser.pro.getPrize();
            }
        }
        return resultado;
    }

    @Override
    public double getIncommings(LocalDate from, LocalDate to) {
        double resultado=0;
        for(Reservation reser: reservas){
            if(reser.ini.compareTo(from)>=0&&reser.ini.compareTo(to)<=0){
                resultado+=reser.pro.getPrize();
            }
        }
        return resultado;
    }

    @Override
    public Map<IClient, Double> resumeAllIncomingsByClient() {
        Map<IClient,Double> listado=new HashMap<>();
        
        for(IClient cli: clientes){
            double cantidad=0;
            for(Reservation re: reservas){
                if(re.cli.equals(cli)){
                    cantidad+=re.pro.getPrize();
                }
            }
            listado.put(cli,cantidad);
        }
       
        
        return listado;
    }

    @Override
    public boolean createProduct(String name, String description, double prize) {
        boolean creado = false;
   
        return creado;
    }

    @Override
    public boolean createMovie(ProductsTypes type, String name, String description, MovieCategory cat, int minAge) {
        boolean result = false, flag = false;

        Pelicula p = new Pelicula(name, description, minAge, minAge, type, cat);

        for (Product peli : productos) {
            if (peli.equals(p)) {
                result = true;
                break;
            }
        }

        if (!result) {
            productos.add(p);
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean createGame(ProductsTypes type, String name, String description, GameCategory cat, int minAge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createClient(String id, String name, String phone, LocalDateTime time) {
        Client c = new Client(id, name, time, phone);
        boolean añadido = false;
        
        if(!existeCliente(id)){
            añadido=clientes.add(c);
        }

        return añadido;
    }

    @Override
    public boolean removeClient(String id) {
        boolean borrado =false;
        if(existeCliente(id)&&!comprobarssiclientetienereserva(devolverClienteExistente(id))){
            borrado=clientes.remove(devolverClienteExistente(id));
        }
        return borrado;
    }
    
    private boolean comprobarssiclientetienereserva(Client c){
        boolean tiene=false;
        for(Reservation reser : reservas){
            if(reser.cli.equals(c)){
                tiene=true;
                break;
            }
        }
        return tiene;
    }
    
    public boolean existeCliente(String id){
        boolean existe=false;
        for (IClient client : clientes) {
            if (client.getID().equals(id)){
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    public Client devolverClienteExistente(String id){
        Client devolver=null;
        for(IClient c: clientes){
            if(c.getID().equals(id)){
                devolver=(Client)c;
                break;
            }
        }
        return devolver;
    }

    @Override
    public boolean editClient(IClient e) {
        boolean editado=false;
        
        return editado;
    }

    @Override
    public boolean addProduct(String name) {
        boolean clonado=false;
        
        return clonado;
    }
    
    public boolean productoExistente(String name){
        boolean existe=false;
        for(Product p: productos){
            if(p.getName().equals(name)){
                existe=true;
                break;
            }
            
        }
        return existe;
    }

    @Override
    public boolean removeProduct(String name) {
        boolean result = false;
        Predicate prueba=new Predicate() {
            @Override
            public boolean test(Object t) {
                boolean procede=false;
                Product produc=(Product)t;
                procede=produc.getName().equals(name);
                return procede;
            }
        };
        
        result=productos.removeIf(prueba);
        
        return result;
    }

    @Override
    public boolean editProduct(String key, Product newP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product isAvailableProduct(String name) {
        Product p=null;
        if(productoExistente(name)){
            for(Product pro: productos){
                if(pro.getName().equals(name)&&pro.getStatus()==Product.Status.AVAILABLE){
                    p=pro;
                    break;
                }
            }
        }
        return p;
    }

    @Override
    public boolean reserveProduct(Product prod, IClient client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double closeReservation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean loadCatalogFromDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean loadClientsFromDDBB() {
        boolean cargado=false;
        this.clientes.clear();
        try {
            File file = new File(clientsDDBB);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // estos métodos podemos usarlos combinados para normalizar el archivo XML
            //getDocumentElement()	Accede al nodo raíz del documento
            //normalize()	Elimina nodos vacíos y combina adyacentes en caso de que los hubiera
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Cliente");   //nList.getLength() -> n_nodos
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //String id=eElement.getAttribute("id");
                    String nombre = eElement.getElementsByTagName("Nombre").item(0).getTextContent();
                    String id = eElement.getElementsByTagName("ID").item(0).getTextContent();
                    String telef = eElement.getElementsByTagName("Telefono").item(0).getTextContent();
                    String fecha=eElement.getElementsByTagName("Fecha").item(0).getTextContent();
                    int mes=Integer.parseInt(fecha.substring(5, 7));
                    
                    Client c = new Client(id, nombre, LocalDateTime.of(Integer.parseInt(fecha.substring(0, 4)),mes
                            , Integer.parseInt(fecha.substring(8, 10)), Integer.parseInt(fecha.substring(11, 13)),
                            Integer.parseInt(fecha.substring(14, 16)),Integer.parseInt(fecha.substring(17, 19))), telef);
                    clientes.add(c);
                }
            }
            cargado=true;
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (SAXException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return cargado;
    }

    @Override
    public boolean loadReservationsFromDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean loadAllDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveCatalogFromDDBB() {
        boolean guardado = false;
        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build;

            build = dFact.newDocumentBuilder();   

            org.w3c.dom.Document doc = build.newDocument(); 

            Element raiz = doc.createElement("Catalogo");

            for (Product c : productos) {
                Element e;
                if (c instanceof Pelicula) {
                    e = doc.createElement("Pelicula");
                    Element cat = doc.createElement("Categoria");
                    cat.appendChild(doc.createTextNode(String.valueOf(((Pelicula) c).getCategory())));
                    e.appendChild(cat);
                } else if (c instanceof Juego) {
                    e = doc.createElement("Juego");
                    Element cat = doc.createElement("Categoria");
                    cat.appendChild(doc.createTextNode(String.valueOf(((Juego) c).getCategory())));
                    e.appendChild(cat);
                } else {
                    e = doc.createElement("Otro");
                }

                Element k = doc.createElement("Key");
                k.appendChild(doc.createTextNode(c.getKey()));
                Element name = doc.createElement("Nombre");
                name.appendChild(doc.createTextNode(c.getName()));
                Element des = doc.createElement("Descripcion");
                des.appendChild(doc.createTextNode(c.getDescription()));
                Element prec = doc.createElement("Precio");
                prec.appendChild(doc.createTextNode(String.valueOf(c.getPrize())));
                Element edad = doc.createElement("EdadMinima");
                edad.appendChild(doc.createTextNode(String.valueOf(c.getPrize())));
                Element tipo = doc.createElement("Tipo");
                tipo.appendChild(doc.createTextNode(String.valueOf(c.getTipo())));
                Element estado = doc.createElement("Estado");
                estado.appendChild(doc.createTextNode(String.valueOf(c.getStatus())));

                e.appendChild(k);
                e.appendChild(name);
                e.appendChild(des);
                e.appendChild(prec);
                e.appendChild(edad);
                e.appendChild(tipo);
                e.appendChild(estado);
                raiz.appendChild(e);

            }
            doc.appendChild(raiz);
            //Guardar el xml en el disco duro
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            //<-- OPCIONES DEL ARCHIVO
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(catalogDDBB));

            trans.transform(source, result);
            guardado = true;

        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);

        }
        return guardado;
    }

    @Override
    public boolean saveClientsFromDDBB() {
        boolean guardado = false;
        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build;

            build = dFact.newDocumentBuilder();   

            org.w3c.dom.Document doc = build.newDocument(); 
            Element raiz = doc.createElement("Clientes");

            for (IClient c : clientes) {
                Element e = doc.createElement("Cliente");

                Element k = doc.createElement("ID");
                k.appendChild(doc.createTextNode(c.getID()));
                e.appendChild(k);
                Element name = doc.createElement("Nombre");
                name.appendChild(doc.createTextNode(c.getName()));
                e.appendChild(name);
                Element tel = doc.createElement("Telefono");
                tel.appendChild(doc.createTextNode(c.getPhone()));
                e.appendChild(tel);
                Element prec = doc.createElement("Fecha");
                prec.appendChild(doc.createTextNode(c.getTime().toString()));

                e.appendChild(prec);

                raiz.appendChild(e);

            }
            doc.appendChild(raiz);

            //Guardar el xml en el disco duro
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            //<-- OPCIONES DEL ARCHIVO
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(clientsDDBB));

            trans.transform(source, result);
            guardado = true;

            
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);

        }
        return guardado;
    }

    @Override
    public boolean saveReservationsFromDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveAllDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
