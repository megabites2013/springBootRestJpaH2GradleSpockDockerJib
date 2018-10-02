import org.openqa.selenium.chrome.ChromeDriver

// Location where Geb saves the screenshots and HTML dumps at the end of each test
reportsDir = 'build/test-reports'

atCheckWaiting = true

// Run tests in Firefox by default
driver = { new ChromeDriver() }

environments {

    // run with "gradlew -Dgeb.env=chrome test"
    // ChromeDriver reference: https://sites.google.com/a/chromium.org/chromedriver/
    chrome {
        // Download and configure ChromeDriver using https://github.com/bonigarcia/webdrivermanager
        ChromeDriverManager.getInstance().setup()

        driver = { new ChromeDriver() }
    }
}