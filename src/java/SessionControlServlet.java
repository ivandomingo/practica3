/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

/**
 *
 * @author ivanDomingo
 */
public class SessionControlServlet extends HttpServlet {

    ServletContext context;
    
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        context = servletConfig.getServletContext();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        Integer numeroAccesos;//cuenta la cantidad de accesos en la sesion actual
        Integer maximoNumAccesos = Integer.parseInt(context.getInitParameter("MaximoNumeroAccesos"));
        //se recupera del context el parametro Maximo Numero de Accesos
        synchronized (session) {
            numeroAccesos = (Integer) session.getAttribute("numeroAccesos");//se recupera el atributo y se guarda en el contador
            if (numeroAccesos == null) {//en caso de que sea null
                numeroAccesos = 1;//se estable a 1
                session.setAttribute("numeroAccesos", numeroAccesos);//se graba en la sesion el atributo numeroAccesos
            } else {// si es distinto de null
                numeroAccesos = new Integer(numeroAccesos + 1);//se le suma 1
                if (numeroAccesos >= maximoNumAccesos) {//si supera o iguala el numero maximo de accesos
                    response.sendRedirect("./accesosMaximos.html");//se le redirige a este html
                }
                session.setAttribute("numeroAccesos", numeroAccesos);//se graba en la sesion el atributo numeroAccesos
            }
            session.setAttribute("numeroAccesos", numeroAccesos);//se graba en la sesion el atributo numeroAccesos
        }

        try {//se imprime por pantalla la cantidad de accesos
            out.println("<html><head></head><body>");
            out.print("<p>Cantidad de accesos: " + numeroAccesos + " en la sesion actual.</p>");
        } finally {//se cierra
            out.close();

        }
    }
}
