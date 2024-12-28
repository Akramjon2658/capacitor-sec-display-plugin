import { WebPlugin } from '@capacitor/core';

import type { SecondaryDisplayPlugin } from './definitions';

export class SecondaryDisplayWeb extends WebPlugin implements SecondaryDisplayPlugin {
  async initialize(): Promise<void> {
    console.log('Secondary display initialization not available on web');
  }

  async showContent(options: { url: string }): Promise<void> {
    console.log('Secondary display not available on web', options);
  }

  async sendMessage(options: { message: Record<string, any> }): Promise<void> {
    console.log('Secondary display not available on web', options);
  }
}