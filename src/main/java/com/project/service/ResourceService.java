/**
 * Class To implement the service layer for the Users
 * @author Pratap Singh Ranawat and Vivek Mittal
 */
package com.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.project.dao.ResourceDAO;
import com.project.model.ResourcesModel;
import com.project.model.ResourcesVO;
import com.project.model.UsersModel;

@Service("resourceService")
@Transactional
public class ResourceService {

	// To interact with the DAO Layer
	@Autowired
	private ResourceDAO resourceDAO;

	// To get the beans
	@Autowired
	private ApplicationContext context;

	/**
	 * To get the list of all available resources from ResourcesDao class
	 * 
	 * @return - List of all the available resources
	 * @author Vivek Mittal, Pratap Singh
	 */

	public List<ResourcesVO> allResourceList() {

		List<ResourcesModel> resourcesModel = new ArrayList<ResourcesModel>();

		// Getting all the available resource list from dao
		resourcesModel = resourceDAO.allResourceList();
		List<ResourcesVO> resourcesVO = new ArrayList<ResourcesVO>(
				resourcesModel.size());
		ResourcesVO resourceVO;

		// Converting the list of ResourceModel to ResourcesVO
		for (int i = 0; i < resourcesModel.size(); i++) {
			resourceVO = context.getBean(ResourcesVO.class);
			BeanUtils.copyProperties(resourcesModel.get(i), resourceVO);
			resourcesVO.add(resourceVO);
		}

		return resourcesVO;
	}

	/**
	 * Following function helps in creating a new resource.
	 * 
	 * @param resourcesVO
	 *            contains the information of the new resource.
	 * @return true/false whether resource created successfully or not.
	 * @author Arpit Pittie
	 */
	public boolean createResource(ResourcesVO resourcesVO) {
		ResourcesModel resourceModel = context.getBean(ResourcesModel.class);

		// Copying properties of ResourcesModel to ResourcesVO
		BeanUtils.copyProperties(resourcesVO, resourceModel);

		return resourceDAO.createResource(resourceModel);
	}

	/**
	 * Following function helps in updating the existing resource.
	 * 
	 * @param resourcesVO
	 *            contains the information of the resource to be updated.
	 * @return true/false whether resource updated successfully or not.
	 * @author Vivek Mittal, Pratap Singh
	 */
	public boolean editResource(ResourcesVO resourcesVO) {
		ResourcesModel resourceModel = context.getBean(ResourcesModel.class);

		// Copying properties of ResourcesVO to ResourcesModel
		BeanUtils.copyProperties(resourcesVO, resourceModel);
		// resourceModel = resourcesVOToResourcesModel(resourcesVO);
		List<UsersModel> newResAdmins = resourceModel.getResourceAdmins();

		// Deleting a resource admin
		resourceDAO.deleteResourceAdmin(resourceModel);

		// Adding a resource Admin
		for (UsersModel usersModel : newResAdmins) {
			resourceDAO.addResourceAdmin(resourceModel, usersModel);
		}

		// Updating the remaining properties of the resource
		return resourceDAO.editResource(resourceModel);
	}
}