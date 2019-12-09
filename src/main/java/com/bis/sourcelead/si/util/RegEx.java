package com.bis.sourcelead.si.util;

public enum RegEx {
	
	ELIMINATE_KEYWORDS("\\b(CURRICULLUM VITAE)|(ORGANIZATION)|Organization|(Curriculum Vitae)|RESUME|Resume\\b"),
	ELIMINATE_RESUME_NAME_KEYWORDS("\\b(RESUME|CV|NAME(:?))\\b"),
	NAME("\\b(CANDIDATE(:?)|FIRST NAME(:?))\\b"),
	
	OBJECTIVE("\\b("
			+ "Career Goal|Objective|career objective|Career Summary|Employment Objective|Highlights|Job Objective|Objective(s?)|Professional Objective|Professional Profile|Professional Summary|Summary of Qualification(s?)|Summary Statement|Technical Summary|Profile|Summary|Career Brief|" 
			+ "CAREER GOAL|OBJECTIVE|CAREER OBJECTIVE|CAREER SUMMARY|EMPLOYMENT OBJECTIVE|HIGHLIGHTS|JOB OBJECTIVE|OBJECTIVE(S?)|PROFESSIONAL OBJECTIVE|PROFESSIONAL PROFILE|PROFESSIONAL SUMMARY|SUMMARY OF QUALIFICATION(S?)|SUMMARY STATEMENT|TECHNICAL SUMMARY|PROFILE|SUMMARY|CAREER BRIEF"
			+ ")\\b"),
	
	PERSONAL_INFORMATION("\\b("
			+ "Personal Information|PERSONAL PROFILE|PERSONAL DETAILS|"
			+ "PERSONAL INFORMATION|Personal Profile|Personal Details"
			+ ")\\b"),
	
	EXPERIENCE("\\b("
			+ "ADDITIONAL EXPERIENCE|ARMY EXPERIENCE|CAREER RELATED EXPERIENCE|EMPLOYMENT HISTORY|EXPERIENCE SUMMARY|FREELANCE|FREELANCE EXPERIENCE|MILITARY BACKGROUND|MILITARY EXPERIENCE|PROFESSIONAL BACKGROUND|PROFESSIONAL EXPERIENCE|PROFESSIONAL WORK EXPERIENCE|RELATED EXPERIENCE|WORK EXPERIENCE|WORK HISTORY|EXPERIENCE DETAILS|JOB SUMMARY|SUMMARY OF EXPERIENCE|EMPLOYMENT|EMPLOYER|"
			+ "Additional Experience|Army Experience|Career Related Experience|Employment History|Experience Summary|Freelance|Freelance Experience|Military Background|Military Experience|Professional Background|Professional Experience|Professional Work Experience|Related Experience|Work Experience|Work History|Experience Details|Job Summary|Summary of Experience|Employment|Employer"
			+ ")\\b"),	
	
	SKILLS("\\b("
			+ "Abilities|Areas of Experience|Areas of Expertise|Areas of Knowledge|Career Related Skills|Computer Knowledge|Computer Skill(s?)|Credentials|Expertise(s?)|Language Competencies and Skills|Professional Skills|Proficiencies|Programming Languages|Qualifications|Skills & Expertise|Specialized Skills|Technical Experience|Technical Skills|Technologies|Tools & Technology|Tools & Technologies|Technical Skill(s?)|Skill(s?)|"
			+ "ABILITIES|AREAS OF EXPERIENCE|AREAS OF EXPERTISE|AREAS OF KNOWLEDGE|CAREER RELATED SKILLS|COMPUTER KNOWLEDGE|COMPUTER SKILL(S?)|CREDENTIALS|EXPERTISE(S?)|LANGUAGE COMPETENCIES AND SKILLS|PROFESSIONAL SKILLS|PROFICIENCIES|PROGRAMMING LANGUAGES|QUALIFICATIONS|SKILLS & EXPERTISE|SKILLS & EXPERTISES|SPECIALIZED SKILLS|TECHNICAL EXPERIENCE|TECHNICAL SKILLS|TECHNOLOGIES|TOOLS & TECHNOLOGY|TOOLS & TECHNOLOGIES|TECHNICAL SKILL(S?)|SKILL(S?)"
			+ ")\\b"),

	INTERESTS("\\b("
			+ "HOBBIES|INTERESTS|MISCELLANEOUS|PERSONAL INTEREST(S?)|PERSONAL STRENGTH(S?)|STRENGTH(S?)|"
			+ "Hobbies|Interests|Miscellaneous|Personal Interest(s?)|Personal Strength(s?)|Strength(s?)"
			+ ")\\b"),

	EXTRA_CURRICULARS("\\b("
			+ "Activities and Honors|Affiliation(s?)|Association(s?)|Activities|Athletic Involvement|Civic Activities|Community Involvement|Extra-Curricular Activities|Honors|Membership(s?)|Professional Activities|Professional Affiliation(s?)|Professional Association(s?)|Professional Membership(s?)|Volunteer Experience|Volunteer Work|"
			+ "ACTIVITIES AND HONORS|AFFILIATION(S?)|ASSOCIATION(S?)|ACTIVITIES|ATHLETIC INVOLVEMENT|CIVIC ACTIVITIES|COMMUNITY INVOLVEMENT|EXTRA-CURRICULAR ACTIVITIES|HONORS|MEMBERSHIP(S?)|PROFESSIONAL ACTIVITIES|PROFESSIONAL AFFILIATION(S?)|PROFESSIONAL ASSOCIATION(S?)|PROFESSIONAL MEMBERSHIP(S?)|VOLUNTEER EXPERIENCE|VOLUNTEER WORK"
			+ ")\\b"),

	EDUCATION("\\b("
			+ "ACADEMIC BACKGROUND|ACADEMIC EXPERIENCE|ACADEMIC TRAINING|ACADEMIC DETAILS|ACADEMIC QUALIFICATION(S?)|APPRENTICESHIPS|CERTIFICATION(S?)|COLLEGE ACTIVITIES|COURSE PROJECT EXPERIENCE|COURSES|EDUCATION|DIPLOMA(S?)|EDUCATION AND TRAINING|EDUCATIONAL BACKGROUND|EDUCATIONAL QUALIFICATIONS|EDUCATIONAL TRAINING|EDUCATION SUMMARY|EDUCATIONAL SUMMARY|EDUCATION DETAIL(S?)|EDUCATIONAL DETAIL(S?)|INTERNSHIP EXPERIENCE|INTERNSHIP(S?)|PATENT(S?)|PROFESSIONAL TRAINING|PROGRAM(S?)|RELATED COURSE PROJECTS|RELATED COURSES|SPECIAL TRAINING|STUDY|TRAINING|"
			+ "Academic Background|Academic Experience|Academic Training|Academic Details|Academic Qualification(s?)|Apprenticeships|Certification(s?)|College Activities|Course Project Experience|Courses|Education|Diploma(s?)|Education and Training|Educational Background|Educational Qualifications|Educational Training|Education Summary|Educational Summary|Education Detail(s?)|Educational Detail(s?)|Internship Experience|Internship(s?)|Patent(s?)|Professional Training|Program(s?)|Related Course Projects|Related Courses|Special Training|Study|Training"
			+ ")\\b"),

	CREDIBILITY("\\b("
			+ "PORTFOLIO|RECOMMENDATIONS|REFERENCES|SOCIAL MEDIA PROFILES|SOCIAL PROFILES|TESTIMONIALS|WEB PORTFOLIO|WEBSITES|"
			+ "Portfolio|Recommendations|References|Social Media Profiles|Social Profiles|Testimonials|Web Portfolio|Websites"
			+ ")\\b"),
	
	AWARDS("\\b("
			+ "Academic Honors|Accolades|Accomplishments|Achievements|Activities and Honors|Awards|Distinctions|Endorsements|Fellowship(S?)|Honors|Scholarship(s?)|"
			+ "ACADEMIC HONORS|ACCOLADES|ACCOMPLISHMENTS|ACHIEVEMENTS|ACTIVITIES AND HONORS|AWARDS|DISTINCTIONS|ENDORSEMENTS|FELLOWSHIP(S?)|HONORS|SCHOLARSHIP(S?)"
			+ ")\\b"),
	
	ACCOMPLISHMENTS("\\b("
			+ "Conference Presentations|Conventions|Current Research Interests|Dissertation(s?)|Exhibits|Licenses|Papers|Presentations|Professional Publications|Publications|Research Grants|Research Interests|Research Projects|Thesis / Theses|"
			+ "CONFERENCE PRESENTATIONS|CONVENTIONS|CURRENT RESEARCH INTERESTS|DISSERTATION(S?)|EXHIBITS|LICENSES|PAPERS|PRESENTATIONS|PROFESSIONAL PUBLICATIONS|PUBLICATIONS|RESEARCH GRANTS|RESEARCH INTERESTS|RESEARCH PROJECTS|THESIS / THESES"
			+ ")\\b"),

	LANGUAGE("\\b("
			+ "Language(s?)|"
			+ "LANGUAGE(S?)"
			+ ")\\b"),
	
    EMAIL("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+"),
    PHONE("\\(?([0-9]{3})\\)?[-. ]([0-9]{3})[-. ]?[-. ]?([0-9]{4})"),
    LINK("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)"),

    DATEFROMTO("([A-Za-z]+\\s)?([0-9]{4})\\s[-]\\s\\b((P|p)resent|(C|c)urrent)\\b|([A-Za-z]+\\s)?([0-9]{4})"),
	ELIMINATE_SKILLS("\\b(very|good|knowledge|at|on|in|and|having|expertise|experience|identifying|with|executed|test|script(s?)|well|quality|center|strong|using|package|developing|development|develop(ers?)|environment|include|about|code|function(al?)|analysis|coverage|cover(s?)|familiar|train(ed?)|training|domain|linguistic)\\b");
	
    /**
     * Note:
     * - if you have a combination of words make sure you put them in the beginning of the list
     */

    private final String name;

    RegEx(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
		return this.name;
	}
}