/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/**
 * @author Ilya Berezhniuk
 */

#include "open/platform_types.h"
#include <windows.h>
#include "port_memaccess.h"


int port_read_memory(void* addr, size_t size, void* buf)
{
    if (!buf || !addr)
        return -1;

    if (size == 0)
        return 0;

    SIZE_T bytes_read;

    BOOL result = ReadProcessMemory(GetCurrentProcess(),
                                    addr, buf, size, &bytes_read);

    if (!result || bytes_read != size)
        return -1;

    return 0;
}

int port_write_memory(void* addr, size_t size, void* buf)
{
    if (!buf || !addr)
        return -1;

    if (size == 0)
        return 0;

    SIZE_T bytes_written;

    BOOL result = WriteProcessMemory(GetCurrentProcess(),
                                    addr, buf, size, &bytes_written);

    if (!result || bytes_written != size)
        return -1;

    FlushInstructionCache(GetCurrentProcess(), addr, size);

    return 0;
}
