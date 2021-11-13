package tn.esprit.spring.test;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

 @RunWith(SpringRunner.class)
 @SpringBootTest
public class DepartementTest {
	private static final Logger L = LogManager.getLogger(DepartementTest.class);


	@Autowired
	IEmployeService employeS;
	@Autowired
	 IEntrepriseService entreService;
	@Autowired
	DepartementRepository departementRerpository;
	private Entreprise entreprise;
	private Departement department;
	

	@Test
	public void ajouterDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get()!= null);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get().getName().equals("production"));
		L.info("Entreprise added successfully!");
		entreService.deleteDepartementById(idDepartement);


	}
	@Test
	public void suprimerDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).isPresent());
		entreService.deleteDepartementById(idDepartement);
		Assert.assertFalse(departementRerpository.findById(idDepartement).isPresent());
	}
	@Test
	public void affecterDepartementAEntrepriseTest ()
	{
		this.entreprise = new Entreprise();
		this.entreprise.setName("entrepriseTest1");
		this.entreprise.setRaisonSocial("raisonTest1");

		this.department= new Departement();
		this.department.setName("departmenTest1");
		int entreId=entreService.ajouterEntreprise(this.entreprise);
		int depId=entreService.ajouterDepartement(this.department);

		entreService.affecterDepartementAEntreprise(depId, entreId);

		L.info("Departement with id=" + depId + " added successfully to Entreprise with id=" + entreId);

		entreService.deleteDepartementById(depId);
		entreService.deleteEntrepriseById(entreId);

	}
	@Test
	public void getAllDepartementsNamesByEntrepriseTest() {
		Departement depTest2 = new Departement("info");
		int depId1=entreService.ajouterDepartement(depTest2);
		this.entreprise = new Entreprise();
		this.entreprise.setName("esprit");
		int entreId=entreService.ajouterEntreprise(this.entreprise);
		entreService.affecterDepartementAEntreprise(depId1, entreId);
		L.info("Start Method getAllDepartementsNamesByEntreprise ");
		List<String>depNames = entreService.getAllDepartementsNamesByEntreprise(entreId);
		L.info(depNames);
		Assert.assertTrue(!depNames.isEmpty());
		entreService.deleteDepartementById(depId1);
		entreService.deleteEntrepriseById(entreId);
		L.info(" End Method getAllDepartementsNamesByEntreprise ");
	}
 }




