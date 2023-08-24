#!/usr/bin/env groovy

def call(Map<String, String> propertyMap, String templatePath, String outputPath) {
  try {
    def templateFile = new File(templatePath)
    if (!templateFile.exists()) {
      error "Template file not found: ${templatePath}"
      return
    }

    def templateContent = templateFile.text
    propertyMap.each { paramName, envVarName ->
      def envVarValue = env."${envVarName}"

      if (envVarValue != null) {
        templateContent = templateContent.replaceAll(paramName, envVarValue)
      } else {
        error "Environment variable ${envVarName} not found."
      }

      def outputFile = new File(outputPath)
      outputFile.text = templateContent
    }
  } catch (Exception e) {
    currentBuild.result = 'FAILURE'
    error "Error in populating template: ${e.message}"
  }
}