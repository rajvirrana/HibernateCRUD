/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/

package com.hibernate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;

public class Myservlet extends HttpServlet {
    private static SessionFactory factory;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        PrintWriter out = response.getWriter();
        try{
            //factory = new AnnotationConfiguration().configure().addAnnotatedClass(Emp.class).buildSessionFactory();
            factory = new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex){
            System.out.println("Error throws");
            throw new ExceptionInInitializerError(ex);
        }
        
       Myservlet ms = new Myservlet();
       if(request.getParameter("action").equalsIgnoreCase("addemp")){
           ms.addEmp(request.getParameter("empname"), Integer.parseInt(request.getParameter("empsalary")),out);
           response.sendRedirect("");
       }else if(request.getParameter("action").equalsIgnoreCase("getEmp")){
            out.print(ms.getEmp(Integer.parseInt(request.getParameter("id"))).toString());
       }else if(request.getParameter("action").equalsIgnoreCase("editEmp")){
            int status = ms.editEmployee(request);
            response.sendRedirect("?edited="+status);
       }else if(request.getParameter("action").equalsIgnoreCase("delete")){
           int status = ms.delEmp(request);
           response.sendRedirect("?deleted="+status);
       }else{
           //List<Emp> emplist = ms.viewEmp();
           //JSONArray jsonarr = new JSONArray(emplist);
           out.write(ms.viewEmp().toString());
       }
       //ms.addEmp(1,"Rana",20000);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addEmp(String name, int salary,PrintWriter out) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Emp employee = new Emp();
            employee.setEmpname(name);
            employee.setEmpsalary(salary);
            List<Certificate> list = new ArrayList<Certificate>();
            list.add(new Certificate("MCA"));
            list.add(new Certificate("MBA"));
            list.add(new Certificate("PMP"));
            employee.setCertificate(list);
            session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if(tx.wasCommitted()){
            out.println("Successfully saved....!");
        }else{
            out.println("Transaction failed...!");
        }
    }
    
    private JSONArray getEmp(Integer id){
        Emp emp = null;
        Session session = factory.openSession();
        Transaction tx = null;
        JSONArray json=null;
        try {
            tx = session.beginTransaction();
            emp = (Emp) session.get(Emp.class, id);
            tx.commit();
           
            List<Emp> emplist = new ArrayList<Emp>();
            emplist.add(emp);
            json = new JSONArray(emplist);
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return json;
    }
    
    private JSONArray viewEmp(){
        List<Emp> mylist = null;
        JSONArray json = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            mylist = session.createQuery("FROM Emp").setCacheable(true).list();
            //mylist = session.createCriteria(Emp.class).list();
            json = new JSONArray(mylist);
            tx.commit();
        } catch (Throwable ex) {
            tx.rollback();
            ex.printStackTrace();
            return null;
        }finally{
            session.close();
        }
        return json;
    }

    private int editEmployee(HttpServletRequest request) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();       
            Emp emp = (Emp) session.get(Emp.class,Integer.parseInt(request.getParameter("id")));
            emp.setEmpname(request.getParameter("editempname"));
            emp.setEmpsalary(Integer.parseInt(request.getParameter("editempsalary")));
            session.update(emp);
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally{
             session.close();
        }    
        
        if(tx.wasCommitted()){
            return 0;
        }else{
            return 1;
        }
    }

    private int delEmp(HttpServletRequest request) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();       
            Emp emp = (Emp) session.get(Emp.class,Integer.parseInt(request.getParameter("id")));
            session.delete(emp);
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }       
        if(tx.wasCommitted()){
            return 0;
        }else{
            return 1;
        }
    }
}
