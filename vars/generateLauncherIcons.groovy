#!/usr/bin/env groovy

def call(String resDirectory, String iconPath) {
  try {
    generateLauncherIcons(resDirectory, iconPath)
  } catch (Exception e) {
    echo "An error occurred while generating icons: ${e.message}"
    currentBuild.result = 'FAILURE'
  }
}

def generateLauncherIcons(String resDirectory, String iconPath) {
  def iconSizes = ["72x72", "96x96", "128x128", "144x144", "192x192"]
  def iconDirectories = ["mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"]

  iconSizes.eachWithIndex { size, index ->
    def outputIconDirectory = "${resDirectory}/${iconDirectories[index]}"

    dir(outputIconDirectory) {
      def outputIcon = "ic_launcher.webp"
      def outputRoundIcon = "ic_launcher_round.webp"

      generateWebP(iconPath, size, outputIcon)
      generateRoundWebP(iconPath, size, outputRoundIcon)
    }
  }
}

def generateWebP(String inputPath, String size, String outputPath) {
  sh "convert ${inputPath} -resize ${size} -filter Lanczos -quality 100 ${outputPath}"
}

def generateRoundWebP(String inputPath, String size, String outputPath) {
  sh "convert ${inputPath} -resize ${size} -alpha set -background none -vignette 0x0 -filter Lanczos -quality 100 ${outputPath}"
}