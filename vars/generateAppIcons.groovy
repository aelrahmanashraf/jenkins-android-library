#!/usr/bin/env groovy

def call(String workspaceDir, String iconPath) {
  def iconSizes = ["72x72", "96x96", "128x128", "144x144", "192x192"]
  def iconDirectories = ["mipmap-hdpi", "mipmap-mdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"]

  for (index in 0..<iconSizes.size()) {
    def size = iconSizes[index]
    def directory = iconDirectories[index]
    def outputDirectory = "${workspaceDir}/app/src/main/res/${directory}"

    dir(outputDirectory) {
      sh "mkdir -p ${outputDirectory}"

      def resizedIcon = "${outputDirectory}/${size}.png"
      def outputIconWebp = "${outputDirectory}/ic_launcher.webp"

      // resize icon
      sh "convert ${iconPath} -resize ${size} ${resizedIcon}"
      sh "cwebp -quiet ${resizedIcon} -o ${outputIconWebp}"

      // delete resized icons after conversion
      sh "rm ${resizedIcon}"
    }
  }
}