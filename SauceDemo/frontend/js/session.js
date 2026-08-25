function getSessionId() {
    let sessionId = localStorage.getItem("saucedemo_session_id");
    if (!sessionId) {
        sessionId = crypto.randomUUID();
        localStorage.setItem("saucedemo_session_id", sessionId);
    }
    return sessionId;
}
