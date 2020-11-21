package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
//    ===================== Your Code Starts Here =====================
//    Note : use "script" to access objects from jenkins pipeline run (WorkflowScript passed from Jenkinsfile)
//           for example: script.node(), script.stage() etc

//    ===================== Parse configuration file ==================

//    ===================== Run pipeline stages =======================

//    ===================== End pipeline ==============================
        script.node(){
            def mvnHome = script.tool name: 'maven_3', type: 'maven'
            script.stage('checkout'){
            script.git 'https://github.com/temtestuser/test-maven-project.git'
           }
            //def content = script.readFile("${configurationFile}")
            //script.println(content)
            def yml = script.readYaml file: "${configurationFile}"
            script.println(yml.build.projectFolder)
            
            script.stage('build'){
                script.sh "${mvnHome}/bin/${yml.build.buildCommand} -f ${yml.build.projectFolder}/pom.xml"
             }
            script.stage('database'){
                script.sh "${mvnHome}/bin/${yml.database.databaseCommand} -f ${yml.database.databaseFolder}/pom.xml"  
            }
            script.stage('deploy'){
                script.sh "${mvnHome}/bin/${yml.deploy.deployCommand} -f ${yml.build.projectFolder}/pom.xml" 
            }
            script.stage('test'){
                script.steps() { 
                    parallel ( 
                        "${yml.test[0].name}" : {
                           script.sh "${mvnHome}/bin/${yml.test[0].testCommand} -f ${yml.test[0].testFolder}/pom.xml"
                        },
                        "${yml.test[1].name}" :{
                            script.sh "${mvnHome}/bin/${yml.test[1].testCommand} -f ${yml.test[1].testFolder}/pom.xml"
                        },
                        "${yml.test[2].name}" :{
                            script.sh "${mvnHome}/bin/${yml.test[2].testCommand} -f ${yml.test[3].testFolder}/pom.xml"
                        }
                            
                    )
                }
                
            }
        }
    }
}
