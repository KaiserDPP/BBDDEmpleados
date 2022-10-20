package com.example.service.local;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.local.EmployeeDTO;
import com.example.entity.local.Department;
import com.example.entity.local.Employee;
import com.example.entity.local.Job;
import com.example.repository.local.DepartmentRepository;
import com.example.repository.local.EmployeeRepository;
import com.example.repository.local.JobRepository;
import com.example.resource.EmailBody;
import com.example.service.email.EmailService;
import com.example.utils.Constants;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;




@Transactional(transactionManager = "postgresTransactionManager")
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	private JobRepository jobRepository;
	private DepartmentRepository departmentRepository;
	private EmailService emailService;
	
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository,
							   JobRepository jobRepository,
							   DepartmentRepository departmentRepository,
							   EmailService emailService) {
		this.employeeRepository = employeeRepository;
		this.jobRepository = jobRepository;
		this.departmentRepository = departmentRepository;
		this.emailService = emailService;
		
	}

	//Buscar uno por ID
	@Override
	public Optional<Employee> findById(int id) {
		return employeeRepository.findById(id);
	}

	//Listar Todos
	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	//Borrar UNO POR ID
	@Override
	public String borrarEmpleado(int id) throws MessagingException {
		if(!employeeRepository.existsById(id)) {
			Constants.message = "¡¡ El Empleado que solicitas NO Existe !!";
		}else {
			//Buscamos el Empleado que existe para borrarlo
			Employee existingEmployee = employeeRepository.findById(id).get();
			
			employeeRepository.delete(existingEmployee);
			Constants.message = "¡¡ El Empleado Ha sido BORRADO !!";
			
			//Envía el email del borrado de un Employee
			EmailBody emailBody = new EmailBody(Constants.msjEmailBajaEmployeeDestinatarios, 
												Constants.asuntoEmailBajaEmployee, 
												Constants.msjEmailBajaEmployee);
			Constants.RutaFicheroAdjunto = "";
			boolean emaiLisent = this.emailService.sendEmail(emailBody, Constants.RutaFicheroAdjunto, Constants.nombreArchivo);
			System.out.println(emaiLisent);
		}
		return Constants.message;
	}

	//Crear
	@Override
	public String crearEmpleado(EmployeeDTO employeeDto, String Trabajo, String Departamento) throws MessagingException {
		//Para crear el Empleado, tenemos que verificar que el trabajo y el departamento existen, sino no se creará
		//si el findByName is present y el nombre de trabajo es el que le pasamos como variable en la url 
		if(buscarTrabajo(Trabajo) == false)  {
			Constants.message = "¡¡ El Trabajo: " + Trabajo + ", NO EXIXTE !! (El Empleado no se creará) ";
		}else {
			//busco el objeto trabajo que ya sé que existe
			Job trabajoExiste = jobRepository.findByName(Trabajo).get();
			
			//si el findByName is present y el nombre del departamento es el que le pasamos como variable en la url
			if(buscarDepartamento(Departamento) == false)  {
				Constants.message = "¡¡ El Departamento: " + Departamento + ", NO EXIXTE !! (El Empleado no se creará) ";
			}else {
				//busco el objeto del departamento que ya sé que existe
				Department departamentoExiste = departmentRepository.findByName(Departamento).get();
				
				Employee employee = new Employee();
				employee.setFirst_name(employeeDto.getFirst_name());
				employee.setLast_name(employeeDto.getLast_name());
				employee.setEmail(employeeDto.getEmail());
				employee.setPhone_number(employeeDto.getPhone_number());
				employee.setHire_date(employeeDto.getHire_date());
				employee.setSalary(employeeDto.getSalary());
				employee.setJobEmployee(trabajoExiste);
				employee.setDepEmployee(departamentoExiste);
				
				employeeRepository.save(employee);
			 
				Constants.message = "¡¡ Empleado Creado con Exito !!";
				//Envía el email del alta de un Employee
				EmailBody emailBody = new EmailBody(Constants.msjEmailAltaEmployeeDestinatarios, 
													Constants.asuntoEmailAltaEmployee, 
													Constants.msjEmailAltaEmployee);
				Constants.RutaFicheroAdjunto = "";
				boolean emaiLisent = this.emailService.sendEmail(emailBody, Constants.RutaFicheroAdjunto, Constants.nombreArchivo);
				System.out.println(emaiLisent);
			}
		}
		return Constants.message;
	}
	
	//busca el Trabajo
	private boolean buscarTrabajo(String Trabajo) {
		if(!jobRepository.findByName(Trabajo).isPresent() || 
		   !jobRepository.findByName(Trabajo).get().getJob_title().equals(Trabajo)) {
			return false;
		} else {
			return true;
		}
	}
	//busca el Departamento
	private boolean buscarDepartamento(String Departamento) {
		if(!departmentRepository.findByName(Departamento).isPresent() || 
		   !departmentRepository.findByName(Departamento).get().getDepartment_name().equals(Departamento)) {
			return false;
		}else {
			return true;
		}
	}
	
	//Modificar
	@Override
	public String modificarEmpleado(EmployeeDTO employeeDto, int id, String Trabajo, String Departamento) throws MessagingException {
		
		//busco al empleado que exista
		if (!employeeRepository.findById(id).isPresent()) {
			Constants.message = "¡¡ El Empleado No Existe, NO lo podemos Modificar !!";
		} else {
			if(buscarTrabajo(Trabajo) == false)  {
				Constants.message = "¡¡ El Trabajo: " + Trabajo + ", NO EXIXTE !! (El Empleado no se Modificará) ";
			}else {
				//busco el objeto trabajo que ya sé que existe
				Job trabajoExiste = jobRepository.findByName(Trabajo).get();
				
				//si el findByName is present y el nombre del departamento es el que le pasamos como variable en la url
				if(buscarDepartamento(Departamento) == false)  {
					Constants.message = "¡¡ El Departamento: " + Departamento + ", NO EXIXTE !! (El Empleado no se Modificará) ";
				}else {
					//busco el objeto del departamento que ya sé que existe
					Department departamentoExiste = departmentRepository.findByName(Departamento).get();
					//busco al empleado que existe para modificarlo
					Employee empleadoExiste = employeeRepository.findById(id).get();
					
					empleadoExiste.setFirst_name(employeeDto.getFirst_name());
					empleadoExiste.setLast_name(employeeDto.getLast_name());
					empleadoExiste.setEmail(employeeDto.getEmail());
					empleadoExiste.setPhone_number(employeeDto.getPhone_number());
					empleadoExiste.setHire_date(employeeDto.getHire_date());
					empleadoExiste.setSalary(employeeDto.getSalary());
					empleadoExiste.setJobEmployee(trabajoExiste);
					empleadoExiste.setDepEmployee(departamentoExiste);
					
					employeeRepository.save(empleadoExiste);
					 
					Constants.message = "¡¡ Empleado Modificado con Exito !!";
					
					//Envía el email de la modificación de un Employee
					EmailBody emailBody = new EmailBody(Constants.msjEmailModifEmployeeDestinatarios, 
														Constants.asuntoEmailModifEmployee, 
														Constants.msjEmailModifEmployee);
					Constants.RutaFicheroAdjunto = "";
					boolean emaiLisent = this.emailService.sendEmail(emailBody, Constants.RutaFicheroAdjunto, Constants.nombreArchivo);
					System.out.println(emaiLisent);

				}
			}
		}
		return Constants.message;
	}
	
	

	@Override
	public String AutoInformeEmpleados() throws IOException, MessagingException {
		
		List<Employee> employeeParaListado = new ArrayList<>();
		employeeParaListado = employeeRepository.findAll();
		
		int TotalEmpleados = 0;
		PdfPTable tablaEmp = new PdfPTable(9);
		tablaEmp.setWidthPercentage(100f);
		tablaEmp.setSpacingBefore(5);
		
		//ENCABEZADO DE LA TABLA
		String[] cabecera = {"id", "NOMBRE", "APELLIDOS", "EMAIL", "TELEFONO", "FECHA-ALTA", "SALARIO", "TRABAJO", "DPTO"};
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		cell.setVerticalAlignment(0);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
		for (int i=0; i < cabecera.length; i++) {
			cell.setPhrase(new Phrase(cabecera[i], font));
			tablaEmp.addCell(cell);
		}
		
		
		for (Employee listaEmpleados : employeeParaListado) {
			//tablaEmp.addCell(String.valueOf(listaEmpleados.getEmployee_id()));
			tablaEmp.addCell(listaEmpleados.getEmployee_id()+"");
			tablaEmp.addCell(listaEmpleados.getFirst_name());
			tablaEmp.addCell(listaEmpleados.getLast_name());
			tablaEmp.addCell(listaEmpleados.getEmail());
			tablaEmp.addCell(listaEmpleados.getPhone_number());
			tablaEmp.addCell(listaEmpleados.getHire_date()+"");
			tablaEmp.addCell(listaEmpleados.getSalary()+"");
			tablaEmp.addCell(listaEmpleados.getJobEmployee().getJob_title());
			tablaEmp.addCell(listaEmpleados.getDepEmployee().getDepartment_name());
			
			TotalEmpleados++;
			
		}
		
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(28.34f, 28.34f, 28.34f, 28.34f);
		
        //PdfWriter.getInstance(document, response.getOutputStream());
		Constants.RutaFicheroAdjunto = "src/main/resources/pdf/InformeEmpleados.pdf";
		Constants.nombreArchivo = "InformeEmpleados.pdf";
		PdfWriter.getInstance(document, new FileOutputStream(Constants.RutaFicheroAdjunto));

        document.open();
        
        //incluir Logotipo MyPeople
        Image image = Image.getInstance ("src/main/resources/img/myPeopleLogo.png");
        image.scaleAbsolute(226, 169);
        document.add(image);
        
        //Incluimos un título al documento
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        
        Paragraph titulo = new Paragraph("Listado de Empleados - MyPeople", fontTitle);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(6);

        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTotal.setSize(12);
        Paragraph totalEmpleadosPgr = new Paragraph("TOTAL DE EMPLEADOS = " + TotalEmpleados, fontTitle);
        totalEmpleadosPgr.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(titulo);
        document.add(Chunk.NEWLINE); //salto de línea
        document.add(tablaEmp);
        document.add(Chunk.NEWLINE); //salto de línea
        document.add(totalEmpleadosPgr); 
        document.close();
		
        //Envía el email del informe de Empleados
		EmailBody emailBody = new EmailBody(Constants.msjEmailInforme1EmployeeDestinatarios, 
											Constants.asuntoEmailInforme1Employee, 
											Constants.msjEmailInforme1Employee);
		
		boolean emaiLisent = this.emailService.sendEmail(emailBody, Constants.RutaFicheroAdjunto, Constants.nombreArchivo);
		System.out.println(emaiLisent);
		
		return "¡¡ Listado Generado y enviado por email !!";
	}
	
}
