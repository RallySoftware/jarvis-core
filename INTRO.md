# Introduction to Jarvis

## Command lifecycle

### Step 1 - is this the command you're looking for?
When jarvis is initialized it performs several actions before it starts listening for messages.

* Load all plugins in the classpath that match the pattern \*/jarvis/plugins/\*
* Create a map of command -> plugin (the plugin is actually the function that will be executed)
* Create a thread pool
* Initialize 1 thread per flow for processing messages
* Initialize thread for reading private messages and global flow notifications