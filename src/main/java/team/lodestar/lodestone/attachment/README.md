# Attachments

Fabric doesn't support capabilities, or even anything similar, so this is an internal implementation of capabilities which, unfortunately, doesn't come with the benefit of other mods being able to access the capability with a sort of soft-dependency and will need a hard dependency instead as this uses interface injection and persistent states.