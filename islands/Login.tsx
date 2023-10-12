import axiod from "https://deno.land/x/axiod@0.26.2/mod.ts";

export default function Login(){
    return <div>
    <button onClick={login}>登陆</button>
    </div>
}


function login() {
    axiod.get("http://localhost:8000/v1/login").then((response) => {
    });
}