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
                script.echo "${build.projectFolder}"
                script.echo"${mvnHome}/bin/${build.buildCommand}"
                
            }
        }
    }
}
