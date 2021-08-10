require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
    s.name         = "react-native-mopub-sdk"
    s.version      = package["version"]
    s.summary      = package["description"]
    s.description  = <<-DESC
    react-native-mopub-sdk
    DESC
    s.homepage     = "https://github.com/aliasad106/React-Native-Mopub-SDK"
    s.license      = "MIT"
    # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
    s.author       = { "author" => "author@domain.cn" }
    s.platform     = :ios, "10.0"
    s.source       = { :git => "https://github.com/aliasad106/React-Native-Mopub-SDK", :tag => "#{s.version}" }
    
    s.resources = "ios/**/*.{xib}"
    s.source_files = "ios/**/*.{h,m}"
    s.requires_arc = true
    
    s.dependency "React"
    
    s.subspec "MoPubSDK" do |ss|
        
        ss.dependency 'mopub-ios-sdk', '5.14.1'
        
        s.static_framework = true
    end
    
    # Required for Native Ads
    s.subspec "AdMob" do |ss|
        ss.dependency 'MoPub-AdMob-Adapters', '7.68.0.0'
    end  
    
    s.subspec "Vungle" do |ss|
        ss.dependency 'MoPub-Vungle-Adapters', '6.8.0.1'
    end
    
    s.subspec "AppLovin" do |ss|
        ss.dependency 'MoPub-Applovin-Adapters', '6.14.4.1'
    end
    
    s.subspec "IronSource" do |ss|
        ss.dependency 'MoPub-IronSource-Adapters', '7.0.3.0.1'
    end
    
    s.subspec "UnityAds" do |ss|
        ss.dependency 'MoPub-UnityAds-Adapters', '3.5.1.0'
    end
end

