export interface SecondaryDisplayPlugin {
  initialize(): Promise<void>;
  showContent(options: { url: string }): Promise<void>;
  sendMessage(options: { message: Record<string, any> }): Promise<void>;
}