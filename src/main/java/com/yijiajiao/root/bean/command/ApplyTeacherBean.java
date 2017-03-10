package com.yijiajiao.root.bean.command;

import java.util.List;

public class ApplyTeacherBean {
	private String userOpenId;
	private String name;
	private String sex;
	private String school;
	private String idCard;
	private String frontPic;
	private String backPic;
	private String idPic;
	private String phone;
	private String invite_code;
	private String subjectName;
	private String subjectCode;
	private String teachAge;
	private String teacher_grade;
	private String provinceCode;
	private String cityCode;
	private String areaCode;
	private int type;// 类型
	private List<GradeBean> grades;
	//享受国务院特殊津贴的专家
	private String allowancePic;
	// 持特级教师证的各学段专家
	private String specialTeachPic;
	//常年从事高考命题的专家
	private String propositionPic;
	//持高级教师资格证的教师
	private String seniorTeachPic;
	//持市、区级学科带头人或兼职教研员证的教师
	private String subjectLeaderPic;
	//市级（含）以上的教学技能大赛获奖的教师
	private String teachSkillPic;
	//有个人已出版的专著或个人特色课程的教师
	private String featureCoursePic;
	//教师资格证
	private String teachQualifyPic;
	//具有三年以上教育教学或培训相关经验者
	private String trainExperiencePic;
	//属于211、985类高校的本科（含）以上学历并有累计1年以上教育实习经验者
	private String famousEducationAndExperiencePic;
	//师范类院校中二等奖学金（含）以上获得者并有累计1年以上教育实习经验者
	private String normalCollegeAndExperiencePic;
	//在当年高考中至少一科是单科状元者
	private String subjectChampionPic;
	//属于211、985类高校的优秀大学生
	private String famousEducationPic;
	//师范类院校中二等奖学金（含）以上获得者并有累计1年以上教育实习经验者
	private String normalCollegePic;

	public String getAllowancePic() {
		return allowancePic;
	}

	public void setAllowancePic(String allowancePic) {
		this.allowancePic = allowancePic;
	}

	public String getSpecialTeachPic() {
		return specialTeachPic;
	}

	public void setSpecialTeachPic(String specialTeachPic) {
		this.specialTeachPic = specialTeachPic;
	}

	public String getPropositionPic() {
		return propositionPic;
	}

	public void setPropositionPic(String propositionPic) {
		this.propositionPic = propositionPic;
	}

	public String getSeniorTeachPic() {
		return seniorTeachPic;
	}

	public void setSeniorTeachPic(String seniorTeachPic) {
		this.seniorTeachPic = seniorTeachPic;
	}

	public String getSubjectLeaderPic() {
		return subjectLeaderPic;
	}

	public void setSubjectLeaderPic(String subjectLeaderPic) {
		this.subjectLeaderPic = subjectLeaderPic;
	}

	public String getTeachSkillPic() {
		return teachSkillPic;
	}

	public void setTeachSkillPic(String teachSkillPic) {
		this.teachSkillPic = teachSkillPic;
	}

	public String getFeatureCoursePic() {
		return featureCoursePic;
	}

	public void setFeatureCoursePic(String featureCoursePic) {
		this.featureCoursePic = featureCoursePic;
	}

	public String getTeachQualifyPic() {
		return teachQualifyPic;
	}

	public void setTeachQualifyPic(String teachQualifyPic) {
		this.teachQualifyPic = teachQualifyPic;
	}

	public String getTrainExperiencePic() {
		return trainExperiencePic;
	}

	public void setTrainExperiencePic(String trainExperiencePic) {
		this.trainExperiencePic = trainExperiencePic;
	}

	public String getFamousEducationAndExperiencePic() {
		return famousEducationAndExperiencePic;
	}

	public void setFamousEducationAndExperiencePic(String famousEducationAndExperiencePic) {
		this.famousEducationAndExperiencePic = famousEducationAndExperiencePic;
	}

	public String getNormalCollegeAndExperiencePic() {
		return normalCollegeAndExperiencePic;
	}

	public void setNormalCollegeAndExperiencePic(String normalCollegeAndExperiencePic) {
		this.normalCollegeAndExperiencePic = normalCollegeAndExperiencePic;
	}

	public String getSubjectChampionPic() {
		return subjectChampionPic;
	}

	public void setSubjectChampionPic(String subjectChampionPic) {
		this.subjectChampionPic = subjectChampionPic;
	}

	public String getFamousEducationPic() {
		return famousEducationPic;
	}

	public void setFamousEducationPic(String famousEducationPic) {
		this.famousEducationPic = famousEducationPic;
	}

	public String getNormalCollegePic() {
		return normalCollegePic;
	}

	public void setNormalCollegePic(String normalCollegePic) {
		this.normalCollegePic = normalCollegePic;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getFrontPic() {
		return frontPic;
	}

	public void setFrontPic(String frontPic) {
		this.frontPic = frontPic;
	}

	public String getBackPic() {
		return backPic;
	}

	public void setBackPic(String backPic) {
		this.backPic = backPic;
	}

	public String getIdPic() {
		return idPic;
	}

	public void setIdPic(String idPic) {
		this.idPic = idPic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getTeachAge() {
		return teachAge;
	}

	public void setTeachAge(String teachAge) {
		this.teachAge = teachAge;
	}

	public String getTeacher_grade() {
		return teacher_grade;
	}

	public void setTeacher_grade(String teacher_grade) {
		this.teacher_grade = teacher_grade;
	}

	public List<GradeBean> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeBean> grades) {
		this.grades = grades;
	}

}
