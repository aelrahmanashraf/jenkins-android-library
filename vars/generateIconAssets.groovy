#!/usr/bin/env groovy

/**
 * This Groovy script generates app icon and splash screen icon assets for an Android app.
 *
 * @param resDirectory The path to the Android app's resource directory.
 * @param imagePath The path to the input icon image.
 */
def call(String resDirectory, String imagePath) {
  try {
    validateInput(resDirectory, imagePath)
    generateIconAssets(resDirectory, imagePath)
  } catch (FileNotFoundException e) {
    echo "Image file not found: ${imagePath}"
    handleFailure()
  } catch (Exception e) {
    echo "An error occurred: ${e.message}"
    handleFailure()
  }
}

/**
 * Validates the input paths.
 *
 * @param resDirectory The path to the Android app's resource directory.
 * @param imagePath The path to the input icon image.
 * @throws FileNotFoundException if the image file does not exist.
 * @throws IllegalArgumentException if the resource directory is invalid.
 */
def validateInput(String resDirectory, String imagePath) {
  if (!new File(imagePath).exists()) {
    throw new FileNotFoundException("Image file not found: ${imagePath}")
  }

  if (!new File(resDirectory).isDirectory()) {
    throw new IllegalArgumentException("Invalid resource directory: ${resDirectory}")
  }
}

/**
 * Generates app icon and splash screen icon assets.
 *
 * @param resDirectory The path to the Android app's resource directory.
 * @param imagePath The path to the input icon image.
 */
def generateIconAssets(String resDirectory, String imagePath) {
  def outputDirectory = "${resDirectory}/drawable"

  dir(outputDirectory) {
    generateAppIcon(imagePath, "app_icon.png")
    generateSplashIcon(imagePath, "splash_icon.png")
  }
}

/**
 * Generates the app icon by resizing the input image to 512x512 pixels.
 *
 * @param inputPath The path to the input icon image.
 * @param outputPath The path where the generated app icon is saved.
 */
def generateAppIcon(String inputPath, String outputPath) {
  sh "convert ${inputPath} -resize 512x512 -filter Lanczos -quality 100 ${outputPath}"
}

/**
 * Generates a modified image suitable for a splash screen by resizing the input image to 512x512 pixels,
 * adding a 380x380 pixel transparent border.
 *
 * @param inputPath The path to the input icon image.
 * @param outputPath The path where the generated splash screen icon is saved.
 */
def generateSplashIcon(String inputPath, String outputPath) {
  sh "convert ${inputPath} -bordercolor transparent -border 380x380 -resize 512x512 -filter Lanczos -quality 100 ${outputPath}"
}

/**
 * Handles a failure by setting the current build result to 'FAILURE'.
 */
def handleFailure() {
  currentBuild.result = 'FAILURE'
}