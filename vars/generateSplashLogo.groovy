#!/usr/bin/env groovy

/**
 * Generates a splash logo for the app's splash screen, optimizing it for use with the Splash Screen API.
 * The generated logo is saved in the 'drawable' directory inside the Android app.
 *
 * @param resDirectory The path to the Android app's resource directory.
 * @param imagePath The path to the input image.
 */
def call(String resDirectory, String imagePath) {
  try {
    validateInput(resDirectory, imagePath)
    generateSplashLogo(resDirectory, imagePath)
  } catch (FileNotFoundException e) {
    echo "Image file not found: ${imagePath}"
    handleFailure()
  } catch (Exception e) {
    echo "An error occurred: ${e.message}"
    handleFailure()
  }
}

def validateInput(String resDirectory, String imagePath) {
  if (!new File(imagePath).exists()) {
    throw new FileNotFoundException("Image file not found: ${imagePath}")
  }

  if (!new File(resDirectory).isDirectory()) {
    throw new IllegalArgumentException("Invalid resource directory: ${resDirectory}")
  }
}


/**
 * Generates a splash logo and adds it to the 'drawable' directory inside the Android app project.
 *
 * @param resDirectory The path to the Android app's resource directory.
 * @param imagePath The path to the input image.
 */
def generateSplashLogo(String resDirectory, String imagePath) {
  def outputDirectory = "${resDirectory}/drawable"

  dir(outputDirectory) {
    generateImageResource(imagePath, "splash_logo.png")
  }
}

/**
 * Generates a modified image by resizing the input image to 512x512 pixels and adding a 180x180 pixel padding,
 * ensuring it is suitable for display on the splash screen.
 *
 * @param inputPath The path to the input image.
 * @param outputPath The path where the generated image is saved.
 */
def generateImageResource(String inputPath, String outputPath) {
  sh "convert ${inputPath} -bordercolor transparent -border 180x180 -resize 512x512 -filter Lanczos -quality 100 ${outputPath}"
}

def handleFailure() {
  currentBuild.result = 'FAILURE'
}