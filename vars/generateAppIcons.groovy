#!/usr/bin/env groovy

def call(String workspaceDir, String iconPath) {
  def iconSizes = ["72x72", "96x96", "128x128", "144x144", "192x192"]
  def iconDirectories = ["mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"]

  iconSizes.eachWithIndex { size, index ->
    def outputDirectory = "${workspaceDir}/app/src/main/res/${iconDirectories[index]}"
    dir(outputDirectory) {
      sh "mkdir -p ${outputDirectory}"
      def resizedImage = "${outputDirectory}/${size}.png"
      def outputFileWebp = "${outputDirectory}/ic_launcher.webp"

      // convert png images to webp format
      sh "convert ${iconPath} -resize ${size} ${resizedImage}"
      sh "cwebp -quiet ${resizedImage} -o ${outputFileWebp}"
      // delete resized images after convertion
      sh "rm ${resizedImage}"
    }
  }
}