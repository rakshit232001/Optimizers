package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.log4j.Logger;

public class TestListener implements ITestListener {
    private static final Logger log = Logger.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("===== STARTING TEST: " + result.getMethod().getMethodName() + " =====");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ TEST PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ TEST FAILED: " + result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠️ TEST SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("===== STARTING TEST SUITE: " + context.getName() + " =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("===== FINISHED TEST SUITE: " + context.getName() + " =====");
    }
}
