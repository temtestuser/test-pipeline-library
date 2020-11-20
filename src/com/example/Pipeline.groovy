package com.example
@Grab('org.yaml:snakeyaml:1.17')

import org.yaml.snakeyaml.Yaml

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
    node {

    script.stage('Initialize') {
      Yaml parser = new Yaml()
      List example = parser.load("configurationFile")
      example.each{println}

    }
    }
    }
}
