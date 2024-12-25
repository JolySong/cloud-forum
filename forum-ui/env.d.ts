/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_BASE_URL: string
  readonly VITE_API_BASE_URL: string
  readonly VITE_STORAGE_SECRET_KEY: string
  readonly VITE_UPLOAD_URL: string
  readonly VITE_USE_PROXY: string
  readonly VITE_WEBSOCKET_URL: string
  readonly VITE_USE_DEBUGGER: string
  readonly VITE_USE_MOCK: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
} 