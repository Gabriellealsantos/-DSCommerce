/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_BACKEND_URL: string;
  readonly VITE_CART_KEY: string;
  readonly VITE_TOKEN_KEY: string;
  readonly VITE_CLIENT_ID: string;
  readonly VITE_CLIENT_SECRET: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}