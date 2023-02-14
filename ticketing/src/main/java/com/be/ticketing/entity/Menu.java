package com.be.ticketing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_menu")
public class Menu extends CommonEntity {
	
	@Id
	@Column(name = "menu_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuId;
	
	@Column(name = "menu_code")
	private String menuCode;
	
	@Column(name = "menu_name")
	private String menuName;
	
	@Column(name = "parent_id")
	private Long parentId;
	
	@Column(name = "menu_desc")
	private String menuDesc;
	
	@Column(name = "menu_cmd")
	private String menuCmd;
	
	@Column(name = "menu_type")
	private String menuType;
	
	@Column(name = "menu_level")
	private Integer menuLevel;
	
	@Column(name = "line_no")
	private Integer lineNo;

	public Menu(Long menuId, String menuCode, String menuName, Long parentId, String menuDesc, String menuCmd,
			String menuType, Integer menuLevel, Integer lineNo) {
		super();
		this.menuId = menuId;
		this.menuCode = menuCode;
		this.menuName = menuName;
		this.parentId = parentId;
		this.menuDesc = menuDesc;
		this.menuCmd = menuCmd;
		this.menuType = menuType;
		this.menuLevel = menuLevel;
		this.lineNo = lineNo;
	}
	
	public Menu() {
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuCmd() {
		return menuCmd;
	}

	public void setMenuCmd(String menuCmd) {
		this.menuCmd = menuCmd;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
}
