# Optimizers

# 🚀 Selenium Capstone Project  

## 📌 Project Overview  
This repository contains the **Capstone Automation Framework** built for demo web application:  
- [SauceDemo](https://www.saucedemo.com/)  

The goal of this project is to design a **robust, end-to-end automated testing and CI/CD workflow** using **Selenium WebDriver + TestNG + BDD Cucumber**. The framework ensures stability, maintainability, and scalability for automated test execution.  

## 🎯 Objectives  
- Automate critical web application functionalities.  
- Validate workflows using **Selenium WebDriver** and **TestNG**.  
- Integrate tests into a **CI/CD pipeline with Jenkins**.  
- Use **GitHub** for version control and collaboration.  
- Manage user stories, test cases, and defects via **Jira + Zephyr**.  
- Provide detailed **ExtentReports** with screenshots on failure.  
- Implement **Cucumber BDD** for behavior-driven testing.  

---

## 🛠️ Tools & Technology Stack  
| Technology          | Purpose                                     |  
|---------------------|---------------------------------------------|  
| **Java**            | Programming language                        |  
| **Selenium WebDriver** | Browser automation                        |  
| **TestNG**          | Testing framework & suite management        |  
| **Maven**           | Build & dependency management               |  
| **Cucumber (BDD)**  | Behavior-driven development with Gherkin    |  
| **Jira + Zephyr**   | Requirement, test case & defect tracking    |  
| **Extent Reports**  | Custom HTML reports with screenshots        |  
| **GitHub**          | Version control & collaboration             |  
| **Jenkins**         | CI/CD automation pipeline                   |  

---

## 📂 Project Structure  
```bash
SauceDemoAutomation/
│── src/main/java
│   ├── pages/              # Page Object classes
│
│── src/test/java
│   ├── tests/          # Test scripts
│   ├── runners/            # TestNG/Cucumber runners
│   ├── stepDefinitions/    # Step defs for BDD
│   ├── features/          # Features files for BDD
│   ├── utils/              # Utilities (Config, Waits, Excel, Listeners)
│
│── src/test/resources/               # Excel test data
│── logs/                   # logs files
│── screenshots/ 
│── pom.xml                 # Maven dependencies
│── testng.xml              # TestNG suite configuration
```

---

## 🔄 Project Flow  
1. **Project Setup**  
   - Create and clone GitHub repository  
   - Setup Maven project structure  

2. **Selenium Automation**  
   - Develop scripts for login, add-to-cart, checkout, and other flows  
   - Follow **Page Object Model (POM)** for maintainability  

3. **Jira Integration**  
   - Create user stories and link test cases  
   - Report bugs with screenshots  

4. **CI/CD with Jenkins**  
   - Pull latest code from GitHub  
   - Run TestNG + Cucumber tests  
   - Generate Extent Reports after build  

5. **Reporting & Analysis**  
   - Evaluate pass/fail trends  
   - Update scripts based on feedback  

---

## 🧪 Framework Approach  
- **Page Object Model (POM):** Structured classes for each page  
- **TestNG XML:** Parallel execution, groups & priorities  
- **Cucumber BDD:** Gherkin-based test scenarios  
- **Data-Driven Testing:** Apache POI for Excel, properties file for configs  
- **Reusable Utilities:** WebDriver setup, waits, logs, reporting, screenshots  
- **Cross-Browser Execution:** Chrome & Firefox supported  
- **Extent Reports:** Graphical HTML reports with screenshots  


---

## 🚀 How to Run Tests  
1. Clone the repository  
   ```bash
   git clone <repo_url>
   cd Automation
   ```  
2. Run tests with Maven  
   ```bash
   mvn clean test
   ```  
  

---

## 📧 Contact  
👤 **Rakshit Bhadoria**  
Java Selenium Batch – 3  
