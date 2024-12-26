// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorSecDisplayPlugin",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorSecDisplayPlugin",
            targets: ["SecondaryDisplayPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "SecondaryDisplayPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/SecondaryDisplayPlugin"),
        .testTarget(
            name: "SecondaryDisplayPluginTests",
            dependencies: ["SecondaryDisplayPlugin"],
            path: "ios/Tests/SecondaryDisplayPluginTests")
    ]
)