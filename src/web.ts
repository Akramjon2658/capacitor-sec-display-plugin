import { WebPlugin } from '@capacitor/core';

import type { SecondaryDisplayPlugin } from './definitions';

export class SecondaryDisplayWeb extends WebPlugin implements SecondaryDisplayPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
