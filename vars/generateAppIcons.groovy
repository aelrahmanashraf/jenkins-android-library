#!/usr/bin/env groovy

def call(String workspaceDir) {
  def iconSizes = ["72x72", "96x96", "128x128", "144x144", "192x192"]
  def iconDirectories = ["mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"]

  iconSizes.eachWithIndex { size, index ->
    def outputDirectory = "${workspaceDir}/app/src/main/res/${iconDirectories[index]}"
    dir(outputDirectory) {
      // Create the directory
    }
  }
}