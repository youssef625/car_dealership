import React, { useState } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import NAVBAR from "../componantes/NAVBAR";

const LOGIN = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (!res.ok) {
        throw new Error("Invalid login credentials");
      }

      const data = await res.json();

    
      localStorage.setItem("token", data.token);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("role", data.role);
      localStorage.setItem("name", data.name);
      localStorage.setItem("email", data.email);

      
      window.location.href = "/";
    } catch  {
      setError("Incorrect Email or Password");
    }
  };

  return (
    <>
      <NAVBAR />

      <main
        className="d-flex justify-content-center align-items-center"
        style={{
          width: "100vw",
          marginLeft: "calc(-50vw + 50%)",
          minHeight: "100vh",
          backgroundColor: "#f1f3f5",
        }}
      >
        <div
          className="container"
          style={{
            maxWidth: "1280px",
            margin: "0 auto",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            height: "100%",
          }}
        >
          <form
            className="p-5 bg-white rounded-4 shadow-lg"
            style={{ width: "100%", maxWidth: "400px" }}
            onSubmit={handleLogin}
          >
            <i
              className="bi bi-car-front-fill mb-4 d-block mx-auto"
              style={{ fontSize: "3rem", color: "#0d6efd" }}
            ></i>

            <h1 className="h4 mb-4 fw-bold text-center">
              Sign in to your account
            </h1>

            {error && (
              <p className="text-danger text-center mb-3">{error}</p>
            )}

            <div className="form-floating mb-3">
              <input
                type="email"
                className="form-control rounded-3"
                id="floatingInput"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="name@example.com"
                required
              />
              <label htmlFor="floatingInput">Email address</label>
            </div>

            <div className="form-floating mb-3">
              <input
                type="password"
                className="form-control rounded-3"
                id="floatingPassword"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                required
              />
              <label htmlFor="floatingPassword">Password</label>
            </div>

            <div className="form-check text-start mb-4">
              <input
                className="form-check-input"
                type="checkbox"
                id="checkDefault"
              />
              <label className="form-check-label" htmlFor="checkDefault">
                Remember me
              </label>
            </div>

            <button className="btn btn-primary w-100 py-2 rounded-3" type="submit">
              Sign in
            </button>

            <p className="mt-5 mb-0 text-body-secondary text-center">
              © 2017–2025 Elite Cars Dealership
            </p>
          </form>
        </div>
      </main>
    </>
  );
};

export default LOGIN;
