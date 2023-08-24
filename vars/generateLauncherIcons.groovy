#!/usr/bin/env groovy

def call(String resDirectory, String iconPath) {
  try {
    generateIcons(resDirectory, iconPath)
  } catch (Exception e) {
    echo "An error occurred while generating icons: ${e.message}"
    currentBuild.result = 'FAILURE'
  }
}

def generateIcons(String resDirectory, String iconPath) {
  def iconSizes = ["72x72", "96x96", "128x128", "144x144", "192x192"]
  def iconDirectories = ["mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"]

  iconSizes.eachWithIndex { size, index ->
    def outputIconDirectory = "${resDirectory}/${iconDirectories[index]}"

    dir(outputIconDirectory) {
      sh "mkdir -p ${outputIconDirectory}"

      def outputIconPath = "${outputIconDirectory}/ic_launcher.webp"
      sh "convert ${iconPath} -resize ${size} -filter Lanczos -quality 90 ${outputIconPath}"
    }
  }
}