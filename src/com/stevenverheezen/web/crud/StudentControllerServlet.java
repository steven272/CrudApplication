package com.stevenverheezen.web.crud;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name = "StudentControllerServlet", urlPatterns = "/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {

    private StudentDBUtil studentDBUtil;

    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            studentDBUtil = new StudentDBUtil(dataSource);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String theCommand = request.getParameter("command");

            switch (theCommand) {
                case "ADD":
                    addStudent(request, response);
                    break;
                default:
                    listStudent(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String theCommand = request.getParameter("command");

            if (theCommand == null) {
                theCommand = "LIST";
            }

            switch (theCommand) {
                case "LIST":
                    listStudent(request, response);
                    break;
                case "LOAD":
                    loadStudent(request, response);
                    break;
                case "UPDATE":
                    updateStudent(request, response);
                    break;
                case "DELETE":
                    deleteStudent(request, response);
                    break;
                case "SEARCH":
                    searchStudent(request, response);
                    break;
                default:
                    listStudent(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void searchStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String theSearchName = request.getParameter("theSearchName");

        List<Student> students = studentDBUtil.searchStudents(theSearchName);

        request.setAttribute("STUDENT_LIST", students);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");
        dispatcher.forward(request, response);

    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String theStudentId = request.getParameter("studentId");

        studentDBUtil.deleteStudent(theStudentId);

        listStudent(request, response);

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Student theStudent = new Student(id, firstName, lastName, email);

        studentDBUtil.updateStudent(theStudent);

        listStudent(request, response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String theStudentId = request.getParameter("studentId");

        Student theStudent = studentDBUtil.getStudent(theStudentId);

        request.setAttribute("THE_STUDENT", theStudent);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");

        dispatcher.forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        Student theStudent = new Student(firstName, lastName, email);

        studentDBUtil.addStudent(theStudent);

        response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Student> students = studentDBUtil.getStudents();

        request.setAttribute("STUDENT_LIST", students);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list_students.jsp");
        dispatcher.forward(request, response);
    }
}
