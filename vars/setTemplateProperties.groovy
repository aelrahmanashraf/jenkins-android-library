#!/usr/bin/env groovy

def call(Map<String, String> propertyMap, String templatePath, String outputPath) {
  def templateFile = new File(templatePath)
  if (!templateFile.exists()) {
    error "Template file not found: ${templateFilePath}"
    return
  }
}