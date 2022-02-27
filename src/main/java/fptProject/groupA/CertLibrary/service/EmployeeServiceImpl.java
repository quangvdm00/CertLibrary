package fptProject.groupA.CertLibrary.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fptProject.groupA.CertLibrary.dao.EmployeeDao;
import fptProject.groupA.CertLibrary.persistence.EmployeeDto;
import fptProject.groupA.CertLibrary.persistence.UserProfileDto;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	@Transactional
	public UserProfileDto findEmployeeProfile(Integer id) {
		return employeeDao.findEmployeeProfile(id);
	}

	@Override
	public List<EmployeeDto> getEmployees() {
		return employeeDao.getEmployees();
	}

}