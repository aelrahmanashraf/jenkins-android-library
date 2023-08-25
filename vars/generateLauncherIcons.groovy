#!/usr/bin/env groovy

def ICON_SIZES = ["72x72", "96x96", "128x128", "144x144", "192x192"]
def ICON_DIRECTORIES = ["mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"]

def call(String resDirectory, String iconPath) {
  try {
    validateInput(resDirectory, iconPath)
    generateLauncherIcons(resDirectory, iconPath)
  } catch (FileNotFoundException e) {
    echo "Icon file not found: ${iconPath}"
    handleFailure()
  } catch (Exception e) {
    echo "An error occurred: ${e.message}"
    handleFailure()
  } finally {
    cleanTempDirectories(resDirectory)
  }
}

def validateInput(String resDirectory, String iconPath) {
  if (!new File(iconPath).exists()) {
    throw new FileNotFoundException("Icon file not found: ${iconPath}")
  }

  if (!new File(resDirectory).isDirectory()) {
    throw new IllegalArgumentException("Invalid resource directory: ${resDirectory}")
  }
}

def generateLauncherIcons(String resDirectory, String iconPath) {
  ICON_SIZES.eachWithIndex { size, index ->
    def outputIconDirectory = "${resDirectory}/${ICON_DIRECTORIES[index]}"

    dir(outputIconDirectory) {
      generateWebP(iconPath, size, "ic_launcher.webp")
      generateRoundWebP(iconPath, size, "ic_launcher_round.webp")
    }
  }
}

def generateWebP(String inputPath, String size, String outputPath) {
  sh "convert ${inputPath} -resize ${size} -filter Lanczos -quality 100 ${outputPath}"
}

def generateRoundWebP(String inputPath, String size, String outputPath) {
  sh "convert ${inputPath} -resize ${size} -alpha set -background none -vignette 0x0 -filter Lanczos -quality 100 ${outputPath}"
}

def cleanTempDirectories(String resDirectory) {
  dir(resDirectory) {
    sh "rm -rf mipmap-*@tmp"
  }
}

def handleFailure() {
  currentBuild.result = 'FAILURE'
}