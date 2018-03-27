import { Tag } from './tag';
import { Bibliography } from './bibliography';
import { Lecturer } from './lecturer';

export interface Project {
    title: string;
    description: string;
    tags: Array<Tag>;
    bibliographies: Array<Bibliography>;
    lecturer: Lecturer;
}

